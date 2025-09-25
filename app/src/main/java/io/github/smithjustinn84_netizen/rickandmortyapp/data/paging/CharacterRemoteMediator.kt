package io.github.smithjustinn84_netizen.rickandmortyapp.data.paging

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil3.SingletonImageLoader
import coil3.request.ImageRequest
import kotlinx.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import androidx.core.net.toUri
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.AppDatabase
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.CharacterEntity
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.RemoteKeys
import io.github.smithjustinn84_netizen.rickandmortyapp.data.mappers.toEntities
import io.github.smithjustinn84_netizen.rickandmortyapp.data.remote.ApiCharacter
import io.github.smithjustinn84_netizen.rickandmortyapp.data.remote.NetworkDataSource

/**
 * A [RemoteMediator] for loading characters from the network and saving them to the local database.
 *
 * This class is responsible for:
 * - Paginating data from the [NetworkDataSource].
 * - Storing fetched data into the [AppDatabase] as [CharacterEntity] objects.
 * - Managing pagination keys ([RemoteKeys]) to keep track of previous and next pages.
 * - Implementing a cache freshness policy in the [initialize] method to determine if a network refresh is needed.
 * - Preloading character images using Coil for a smoother user experience.
 *
 * @property context The application [Context], used for preloading images.
 * @property database The [AppDatabase] instance for accessing DAOs
 *([io.github.smithjustinn84_netizen.rickandmortyapp.data.local.dao.CharacterDao] and [io.github.smithjustinn84_netizen.rickandmortyapp.data.local.dao.RemoteKeysDao]).
 * @property networkService The [NetworkDataSource] for fetching character data from the remote API.
 */
@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val context: Context,
    private val database: AppDatabase,
    private val networkService: NetworkDataSource,
) : RemoteMediator<Int, CharacterEntity>() {

    companion object {
        /** The starting page index for API requests when no previous pagination key is available. */
        private const val STARTING_PAGE_INDEX = 1
    }

    private val userDao = database.characterDao()
    private val remoteKeysDao = database.remoteKeysDao()

    /**
     * Loads data from the network based on the [LoadType] and [PagingState].
     *
     * This function determines which page to fetch from the [networkService] based on the current [loadType]
     * and existing [RemoteKeys].
     * - For [LoadType.REFRESH], it attempts to load data around the current anchor position or defaults to [STARTING_PAGE_INDEX].
     * - For [LoadType.PREPEND], it fetches the page indicated by the `prevKey` of the first item's [RemoteKeys].
     * - For [LoadType.APPEND], it fetches the page indicated by the `nextKey` of the last item's [RemoteKeys].
     *
     * Fetched data is then stored in the local database along with updated [RemoteKeys].
     * Character images are preloaded to improve perceived performance.
     *
     * @param loadType The type of load request ([LoadType.REFRESH], [LoadType.PREPEND], [LoadType.APPEND]).
     * @param state The current [PagingState] which provides information about loaded pages and anchor position.
     * @return A [MediatorResult] indicating success (with endOfPaginationReached status) or failure (with an error).
     */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        try {
            Log.d("CharacterRemoteMediator", "loadType: $loadType")
            val pageToFetch: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    // If remoteKeys for anchor are found, try to refresh around that page.
                    // Otherwise, start from the beginning.
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    // If prevKey is null, it means we're at the beginning of the list.
                    val prevKey = remoteKeys?.prevKey
                    prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    // If nextKey is null, it means we're at the end of the list or list is empty.
                    if (remoteKeys?.nextKey != null) {
                        remoteKeys.nextKey
                    } else if (remoteKeys == null && state.pages.all { it.data.isEmpty() }) {
                        // Initial load or load after a full clear where no items were previously loaded.
                        STARTING_PAGE_INDEX
                    } else {
                        // End of pagination reached if nextKey is null and list wasn't empty initially.
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
            }

            // Fetch characters and page info from the network service.
            // Assumes networkService.loadCharacters returns a Pair of (PageInfo, List<ApiCharacter>).
            val (pageInfo, apiCharacters) = networkService.loadCharacters(pageToFetch)
            val characterEntities = apiCharacters.toEntities()
            val endOfPaginationReached = parsePageNumberFromUrl(pageInfo.next) == null

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // Clear old data and remote keys on refresh.
                    userDao.clearAll()
                    remoteKeysDao.clearRemoteKeys()
                }

                // Insert new characters.
                userDao.insertAll(characterEntities)

                // Create and insert new remote keys for the fetched characters.
                val prevKeyForDb = parsePageNumberFromUrl(pageInfo.prev)
                val nextKeyForDb = parsePageNumberFromUrl(pageInfo.next)
                val keysToInsert = characterEntities.map { entity ->
                    RemoteKeys(
                        characterId = entity.id,
                        prevKey = prevKeyForDb,
                        nextKey = nextKeyForDb
                        // createdAt is defaulted in RemoteKeys data class
                    )
                }
                remoteKeysDao.insertAll(keysToInsert)
            }

            // Preload images for the fetched characters.
            preloadImages(apiCharacters.imageUrls, context)

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            // Network or I/O errors.
            return MediatorResult.Error(e)
        } catch (e: UnknownHostException) {
            // Host resolution errors (often no internet).
            return MediatorResult.Error(e)
        }
    }

    /** Extracts a list of image URLs from a list of [ApiCharacter] objects. */
    private val List<ApiCharacter>.imageUrls get() = map(ApiCharacter::image)

    /**
     * Initializes the [RemoteMediator] and determines if a data refresh is necessary.
     *
     * This method checks:
     * 1. If the [RemoteKeys] table or the character table in the database is empty (e.g., first launch).
     * 2. If the cached data is stale based on a defined timeout (1 hour).
     *    The timestamp of the last update is retrieved using [RemoteKeysDao.getLatestCreationTime].
     *
     * @return [InitializeAction.LAUNCH_INITIAL_REFRESH] if a refresh is needed,
     *         otherwise [InitializeAction.SKIP_INITIAL_REFRESH].
     */
    override suspend fun initialize(): InitializeAction {
        // Define cache timeout (e.g., 1 hour).
        val cacheTimeoutMillis = TimeUnit.HOURS.toMillis(1)

        // Get the timestamp of the last data update from RemoteKeysDao.
        val lastUpdated = database.remoteKeysDao().getLatestCreationTime()

        // If remote keys are not found (e.g., first launch or database cleared)
        // or if the character table is empty, then trigger a refresh.
        if (lastUpdated == null || database.characterDao().isEmpty()) {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

        // If cache is older than the defined timeout, trigger a refresh.
        val isCacheStale = (System.currentTimeMillis() - lastUpdated) > cacheTimeoutMillis
        return if (isCacheStale) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    /**
     * Parses a page number from a URL string (e.g., "https://api.example.com/items?page=2").
     *
     * @param url The URL string, which may be null.
     * @return The integer page number if found and parsable, otherwise null.
     */
    private fun parsePageNumberFromUrl(url: String?): Int? {
        return url?.let { it.toUri().getQueryParameter("page")?.toIntOrNull() }
    }

    /**
     * Retrieves the [RemoteKeys] for the last item in the current [PagingState].
     *
     * @param state The current [PagingState].
     * @return The [RemoteKeys] for the last item, or null if not found or list is empty.
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        // Find the last page with data, then the last item in that page.
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character -> // If such an item exists, get its remote key.
                database.withTransaction { remoteKeysDao.remoteKeysCharacterId(character.id) }
            }
    }

    /**
     * Retrieves the [RemoteKeys] for the first item in the current [PagingState].
     *
     * @param state The current [PagingState].
     * @return The [RemoteKeys] for the first item, or null if not found or list is empty.
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        // Find the first page with data, then the first item in that page.
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character -> // If such an item exists, get its remote key.
                database.withTransaction { remoteKeysDao.remoteKeysCharacterId(character.id) }
            }
    }

    /**
     * Retrieves the [RemoteKeys] for the item closest to the current anchor position in the [PagingState].
     * This is useful for [LoadType.REFRESH] to load data around the user's current view.
     *
     * @param state The current [PagingState].
     * @return The [RemoteKeys] for the closest item, or null if anchor position or item is not found.
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                database.withTransaction { remoteKeysDao.remoteKeysCharacterId(characterId) }
            }
        }
    }
}

/**
 * Preloads images from the given list of URLs using Coil.
 * This helps in making images available in the cache before they are displayed on the UI.
 *
 * @param urls A list of image URLs to preload.
 * @param context The application [Context] required by Coil's ImageRequest.Builder.
 */
private fun preloadImages(urls: List<String>, context: Context) {
    urls.forEach { url ->
        val request = ImageRequest.Builder(context)
            .data(url)
            // Potentially add transformations or other Coil options here if needed.
            .build()
        SingletonImageLoader.get(context).enqueue(request)
    }
}
