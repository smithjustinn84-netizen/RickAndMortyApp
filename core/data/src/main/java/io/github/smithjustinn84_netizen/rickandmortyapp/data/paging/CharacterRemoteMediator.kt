package io.github.smithjustinn84_netizen.rickandmortyapp.data.paging

import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.github.smithjustinn84_netizen.rickandmortyapp.database.AppDatabase
import io.github.smithjustinn84_netizen.rickandmortyapp.database.model.CharacterEntity
import io.github.smithjustinn84_netizen.rickandmortyapp.database.model.RemoteKeys
import io.github.smithjustinn84_netizen.rickandmortyapp.data.mappers.toEntities
import io.github.smithjustinn84_netizen.rickandmortyapp.network.NetworkDataSource
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * A [RemoteMediator] for loading characters from the network and saving them to the local database.
 */
@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val database: AppDatabase,
    private val networkService: NetworkDataSource,
) : RemoteMediator<Int, CharacterEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private val CACHE_TIMEOUT_MS = TimeUnit.HOURS.toMillis(1)
    }

    private val userDao = database.characterDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        try {
            val pageToFetch: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    if (prevKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    when {
                        nextKey != null -> nextKey
                        remoteKeys == null && state.pages.all { it.data.isEmpty() } -> STARTING_PAGE_INDEX
                        else -> return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
            }

            val (pageInfo, apiCharacters) = networkService.loadCharacters(pageToFetch)
            val characterEntities = apiCharacters.toEntities()
            val endOfPaginationReached = parsePageNumberFromUrl(pageInfo.next) == null

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.clearAll()
                    remoteKeysDao.clearRemoteKeys()
                }

                userDao.insertAll(characterEntities)

                val prevKeyForDb = parsePageNumberFromUrl(pageInfo.prev)
                val nextKeyForDb = parsePageNumberFromUrl(pageInfo.next)
                val keysToInsert = characterEntities.map { entity ->
                    RemoteKeys(
                        characterId = entity.id,
                        prevKey = prevKeyForDb,
                        nextKey = nextKeyForDb
                    )
                }
                remoteKeysDao.insertAll(keysToInsert)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: UnknownHostException) {
            return MediatorResult.Error(IOException("No internet connection", e))
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = remoteKeysDao.getLatestCreationTime()
        val now = System.currentTimeMillis()
        val shouldRefresh = lastUpdated == null || (now - lastUpdated) >= CACHE_TIMEOUT_MS
        return if (shouldRefresh) InitializeAction.LAUNCH_INITIAL_REFRESH else InitializeAction.SKIP_INITIAL_REFRESH
    }

    private fun parsePageNumberFromUrl(url: String?): Int? {
        if (url == null) return null
        val page = runCatching { url.toUri().getQueryParameter("page") }.getOrNull()
        return page?.toIntOrNull()
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { character ->
            remoteKeysDao.remoteKeysCharacterId(character.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { character ->
            remoteKeysDao.remoteKeysCharacterId(character.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                remoteKeysDao.remoteKeysCharacterId(characterId)
            }
        }
    }
}
