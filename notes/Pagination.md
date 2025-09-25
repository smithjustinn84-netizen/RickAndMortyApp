# Pagination

Pagination is a technique used to divide a large dataset into smaller, more manageable chunks or "pages." This is especially important in mobile applications to improve performance, reduce memory usage, and enhance user experience when dealing with long lists of data.

Here's a breakdown of how it generally works and how it's often implemented in Android development, especially with libraries like the Jetpack Paging library:

**Core Concepts:**

1.  **Data Source:** This is where your entire dataset resides. It could be a remote server API, a local Room database, or any other source.
2.  **Page Size:** You define how many items constitute a single "page" of data.
3.  **Loading Pages:**
    *   **Initial Load:** When the user first accesses the list, the application loads the first page of data.
    *   **Scrolling/Trigger:** As the user scrolls towards the end of the currently displayed list, the application triggers a request to load the next page of data.
    *   **Appending Data:** The newly loaded data is appended to the existing list, making the scroll seamless for the user.
4.  **Placeholders (Optional but Recommended):** While new data is being loaded, placeholders can be shown in the UI. This provides a better user experience by indicating that more content is coming and maintaining the scroll position.
5.  **Error Handling & Retries:** Mechanisms to handle network errors or other issues during page loading, often with options to retry.
6.  **Refresh:** A way for the user to reload the entire dataset from the beginning, typically via a "swipe-to-refresh" gesture.

**Why Use Pagination?**

*   **Improved Performance:** Loading only a subset of data at a time is faster than fetching everything at once. This leads to quicker initial screen loads.
*   **Reduced Memory Usage:** Holding only the necessary data in memory prevents OutOfMemoryErrors, especially with very large datasets.
*   **Lower Network Usage:** Fetching data in smaller chunks reduces the amount of data transferred over the network at any given time.
*   **Better User Experience:** Users see content quickly and can start interacting with it while more data loads in the background. Infinite scrolling, a common pagination pattern, feels natural.

**Jetpack Paging Library (Common Android Implementation):**

The Jetpack Paging library (you have `androidx.paging:paging-runtime`, `androidx.room:room-paging`, and `androidx.paging:paging-compose` in your dependencies) simplifies implementing pagination in Android. Its key components are:

1.  **`PagingSource`**:
    *   This is the core component responsible for loading pages of data from your data source (network, database, etc.).
    *   You implement a `PagingSource` to define how to fetch a specific page given a key (e.g., page number or an item offset) and the requested page size.
    *   It returns a `LoadResult` which can be `LoadResult.Page` (with the data and next/previous keys), `LoadResult.Error`, or `LoadResult.Invalid` (if the data is no longer valid and needs a full refresh).
    *   For Room, you don't even need to write a custom `PagingSource` for simple queries; Room can generate one for you if your DAO method returns a `PagingSource<Key, Value>`.

2.  **`RemoteMediator` (Optional):**
    *   Used when you need to page data from a network source and cache it in a local database (like Room).
    *   It coordinates loading data from the network when the local database runs out of items or when a refresh is triggered.
    *   It handles saving network data to the local database and managing page keys for network requests.

3.  **`Pager`**:
    *   This is the entry point for constructing a `PagingData` stream.
    *   You configure it with a `PagingConfig` (which defines page size, prefetch distance, placeholder behavior, etc.) and a function that provides your `PagingSource` (and optionally a `RemoteMediator`).
    *   It produces a `Flow<PagingData<Value>>`.

4.  **`PagingData`**:
    *   A container for a snapshot of paged data. It's emitted by the `Pager`'s `Flow`.
    *   This is what your UI layer (e.g., ViewModel and Composable functions) will observe.

5.  **UI Integration (e.g., with Jetpack Compose):**
    *   You collect the `Flow<PagingData<Value>>` in your ViewModel.
    *   In your Composable UI, you use functions like `collectAsLazyPagingItems()` (from `androidx.paging:paging-compose`) to get a `LazyPagingItems` instance.
    *   `LazyPagingItems` can then be used with `LazyColumn` or `LazyRow` to display the data. It automatically handles requesting new pages as the user scrolls.

**Example Flow with Jetpack Paging and Room (Simplified):**

1.  **Entity (like your `CharacterEntity.kt`):** Defines the data structure.
2.  **DAO (Data Access Object):**

```kotlin
    @Dao
    interface CharacterDao {
        @Query("SELECT * FROM character ORDER BY name ASC")
        fun getAllCharacters(): PagingSource<Int, CharacterEntity> // Room generates PagingSource

        @Upsert // Or @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(characters: List<CharacterEntity>)

        @Query("DELETE FROM character")
        suspend fun clearAll()
    }
```

```kotlin
    class CharacterRepository @Inject constructor(
        private val characterDao: CharacterDao,
        // private val networkService: YourNetworkService // If using RemoteMediator
    ) {
        fun getCharactersStream(): Flow<PagingData<CharacterEntity>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20, // How many items to load per page
                    enablePlaceholders = false // Or true, depending on your needs
                ),
                // remoteMediator = if needed, YourRemoteMediator(...)
                pagingSourceFactory = { characterDao.getAllCharacters() }
            ).flow
        }
    }
```

```kotlin
    @HiltViewModel
    class CharacterViewModel @Inject constructor(
        private val repository: CharacterRepository
    ) : ViewModel() {
        val characters: Flow<PagingData<CharacterEntity>> = repository.getCharactersStream()
            .cachedIn(viewModelScope) // Cache PagingData in ViewModelScope
    }
```

```kotlin
    @Composable
    fun CharacterListScreen(viewModel: CharacterViewModel) {
        val characters: LazyPagingItems<CharacterEntity> = viewModel.characters.collectAsLazyPagingItems()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // Use the items extension function for LazyPagingItems
            items(
                count = characters.itemCount,
                key = characters.itemKey { character -> character.id } // Provide a stable key
            ) { index ->
                val character = characters[index]
                if (character != null) {
                    // Your Composable item for each character
                    Text(text = character.name)
                    // Add more details, image, etc.
                } else {
                    // Optional: Display a placeholder while loading
                    Text(text = "Loading...")
                }
            }

            // You can also observe loading states from `characters.loadState`
            // to show full-screen loading indicators, error messages, or retry buttons.
            // For example:// when (val refreshState = characters.loadState.refresh) {
            //     is LoadState.Loading -> { item { FullScreenLoadingIndicator() } }
            //     is LoadState.Error -> { item { ErrorMessageWithRetry(onClick = { characters.retry() }) } }
            //     else -> {}
            // }
            // Similar for append state: characters.loadState.append
        }
    }
```
