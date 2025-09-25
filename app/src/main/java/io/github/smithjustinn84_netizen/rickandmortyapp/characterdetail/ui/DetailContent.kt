package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.annotation.ExperimentalCoilApi
import android.content.res.Configuration
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.Character
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.previewCharacter

/**
 * Composable function that displays the main content of the detail screen,
 * including the header and character details.
 *
 * @param character The [Character] object to display.
 * @param modifier Modifier for this composable. It should include padding for insets.
 * @param onBackClick Callback invoked when the back button in the header is clicked.
 */
@Composable
fun DetailContent(
    character: Character,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (this@BoxWithConstraints.maxHeight > this@BoxWithConstraints.maxWidth) {
                DetailHeader(
                    characterName = character.name,
                    onBackClick = onBackClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            DetailView(
                character = character,
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DetailScreenPreviewPortrait() {
    ProvidePreview {
        DetailContent(character = previewCharacter, onBackClick = {})
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Portrait Dark"
)
@Composable
fun DetailScreenPreviewPortraitDark() {
    ProvidePreview {
        DetailContent(character = previewCharacter, onBackClick = {})
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true, widthDp = 640, heightDp = 360)
@Composable
fun DetailScreenPreviewLandscape() {
    ProvidePreview {
        DetailContent(character = previewCharacter, onBackClick = {})
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(
    showBackground = true,
    widthDp = 640,
    heightDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Landscape Dark"
)
@Composable
fun DetailScreenPreviewLandscapeDark() {
    ProvidePreview {
        DetailContent(character = previewCharacter, onBackClick = {})
    }
}
