package io.github.smithjustinn84_netizen.rickandmortyapp.characters.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.component.ErrorState
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.component.Loading
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.component.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import io.github.smithjustinn84_netizen.rickandmortyapp.R
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.previewCharacters
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.preview.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.Character
import coil3.imageLoader
import coil3.request.ImageRequest
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext


/**
 * Composable function that displays the main character listing screen.
 * It handles loading states (refresh, append errors) and displays either an error screen,
 * a loading screen, or the character content.
 *
 * @param modifier Modifier for this composable, applied to the Scaffold.
 * @param viewModel The [CharacterViewModel] used to fetch character data.
 * @param onClick Callback invoked when a character row is clicked, providing the character\'s ID.
 */
@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel(),
    onClick: (Int) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyPagingItems = viewModel.pager.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.safeDrawing, // Explicitly set contentWindowInsets
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->

        val refreshState = lazyPagingItems.loadState.refresh

        when (refreshState) {
            is LoadState.Loading -> {
                Loading(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    label = stringResource(R.string.loading)
                )
            }

            is LoadState.Error -> {
                ErrorState(
                    message = stringResource(R.string.error_generic),
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    retryLabel = stringResource(R.string.retry),
                    onRetry = { lazyPagingItems.retry() }
                )
            }

            else -> { // NotLoading or Idle (includes LoadState.NotLoading)
                CharacterContent(
                    characters = lazyPagingItems,
                    modifier = Modifier.padding(innerPadding),
                    onClick = onClick
                )
            }
        }

        // Handle append errors with a Snackbar, doesn't need to fill the screen or use innerPadding here
        if (lazyPagingItems.loadState.append is LoadState.Error) {
            Notification(
                snackbarHostState,
                message = stringResource(R.string.error_loading_more_characters),
                actionLabel = stringResource(R.string.retry),
                onAction = { lazyPagingItems.retry() },
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}

/**
 * Composable function that displays the list of characters.
 *
 * @param characters The [LazyPagingItems] containing the character data.
 * @param modifier Modifier for this composable. This will include padding from the Scaffold.
 * @param onClick Callback invoked when a character row is clicked, providing the character\'s ID.
 */
@Composable
fun CharacterContent(
    characters: LazyPagingItems<Character>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    // Preload character images with Coil for currently loaded items to improve scroll performance.
    // This runs whenever the snapshot of loaded items changes and only fetches URLs not seen before.
    val context = LocalContext.current
    val imageLoader = context.imageLoader
    val preloaded = remember { mutableSetOf<String>() }
    val snapshot = characters.itemSnapshotList.items
    LaunchedEffect(snapshot) {
        snapshot.forEach { item ->
            val url = item.image
            if (preloaded.add(url)) {
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .build()
                imageLoader.enqueue(request)
            }
        }
    }

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { CharacterHeader() }
            items(
                count = characters.itemCount,
                key = { index -> characters.peek(index)?.id ?: (index + 1) }
            ) { index ->
                characters[index]?.let { character ->
                    CharacterRow(
                        character = character,
                        onClick = onClick
                    )
                }
            }
            if (characters.loadState.append is LoadState.Loading) {
                item { CircularProgressIndicator() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterContentPreview() {
    val fakeData = previewCharacters
    val pagingData = PagingData.from(fakeData)
    val fakeDataFlow = MutableStateFlow(pagingData)
    ProvidePreview {
        CharacterContent(
            characters = fakeDataFlow.collectAsLazyPagingItems(),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun CharacterContentDarkPreview() {
    val fakeData = previewCharacters
    val pagingData = PagingData.from(fakeData)
    val fakeDataFlow = MutableStateFlow(pagingData)
    ProvidePreview {
        CharacterContent(
            characters = fakeDataFlow.collectAsLazyPagingItems(),
            modifier = Modifier
        )
    }
}

