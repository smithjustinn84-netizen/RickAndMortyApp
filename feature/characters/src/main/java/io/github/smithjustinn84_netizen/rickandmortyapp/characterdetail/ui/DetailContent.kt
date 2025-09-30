package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.ui

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.Character
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.previewCharacter
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.preview.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.feature.characters.R

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
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.detail_padding)),
    ) {
        item {
            DetailHeader(
                characterName = character.name,
                onBackClick = onBackClick
            )
            Row(
                modifier = Modifier
                    .border(2.dp, character.status.color, shape = MaterialTheme.shapes.small)
                    .padding(
                        horizontal = dimensionResource(R.dimen.detail_item_spacing),
                        vertical = dimensionResource(R.dimen.vertical_margin)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status: ",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = character.status.name,
                    style = MaterialTheme.typography.displaySmall,
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.detail_item_spacing)))
        }

        item {
            CharacterImage(
                imageUrl = character.image,
                characterName = character.name,
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.detail_item_spacing)))
        }

        item {
            Details(
                character = character,
                textAlign = TextAlign.Start,
                labelTextStyle = MaterialTheme.typography.labelMedium,
                valueTextStyle = MaterialTheme.typography.titleMedium,
                spacing = dimensionResource(R.dimen.detail_item_spacing),
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
