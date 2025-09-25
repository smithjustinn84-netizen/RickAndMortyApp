package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.ui

import io.github.smithjustinn84_netizen.rickandmortyapp.R
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.previewCharacter
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.Character
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.previewHandler

/**
 * Main composable for the Character Detail Screen.
 * It adapts its layout based on the available screen width.
 *
 * @param character The [Character] data to display.
 * @param modifier Modifier for this composable.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailView(
    character: Character,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWideScreen = this.maxWidth > 600.dp

        when {
            isWideScreen -> WideDetailView(character)
            else -> CompactDetailView(character)
        }
    }
}

/**
 * Displays the character details in a wide layout, suitable for larger screens.
 *
 * @param character The [Character] data to display.
 */
@Composable
private fun WideDetailView(character: Character) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.detail_padding))
            .verticalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CharacterImage(
            imageUrl = character.image,
            characterName = character.name,
            modifier = Modifier.size(width = 250.dp, height = 300.dp)
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.detail_padding)))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.displayMedium,
            )
            Details(
                character = character,
                textAlign = TextAlign.Start,
                labelTextStyle = MaterialTheme.typography.labelSmall,
                valueTextStyle = MaterialTheme.typography.headlineSmall,
                spacing = dimensionResource(R.dimen.small_space),
                isItemLayoutHorizontal = true
            )
        }
    }
}

/**
 * Displays the character details in a compact layout, suitable for smaller screens.
 *
 * @param character The [Character] data to display.
 */
@Composable
private fun CompactDetailView(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.detail_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.displayMedium,
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.detail_padding)))

        CharacterImage(
            imageUrl = character.image,
            characterName = character.name,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.detail_padding)))

        Details(
            character = character,
            textAlign = TextAlign.Start,
            labelTextStyle = MaterialTheme.typography.labelMedium,
            valueTextStyle = MaterialTheme.typography.titleMedium,
            spacing = dimensionResource(R.dimen.detail_item_spacing),
            isItemLayoutHorizontal = false
        )
    }
}

/**
 * Displays the character's image using AsyncImage.
 *
 * @param imageUrl URL of the character's image.
 * @param characterName Name of the character, used for content description.
 * @param modifier Modifier for this composable.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun CharacterImage(
    imageUrl: String,
    characterName: String,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.image_description, characterName),
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(RoundedCornerShape(16.dp))
        )
    }
}

/**
 * Composable that groups and displays all character details using [DetailItem].
 *
 * @param character The [Character] data.
 * @param textAlign Default [TextAlign] for the character's name and items in vertical layout.
 * @param labelTextStyle [TextStyle] for the labels in [DetailItem].
 * @param valueTextStyle [TextStyle] for the values in [DetailItem].
 * @param spacing [Dp] spacing between detail items.
 * @param isItemLayoutHorizontal If true, [DetailItem]s are laid out horizontally.
 */
@Composable
private fun Details(
    character: Character,
    textAlign: TextAlign,
    labelTextStyle: TextStyle,
    valueTextStyle: TextStyle,
    spacing: Dp,
    isItemLayoutHorizontal: Boolean,
) {
    Spacer(modifier = Modifier.height(spacing))
    DetailItem(
        labelString = stringResource(R.string.character_detail_status_label),
        value = character.status.toString(),
        labelTextStyle = labelTextStyle,
        valueTextStyle = valueTextStyle,
    )
    Spacer(modifier = Modifier.height(spacing))
    DetailItem(
        labelString = stringResource(R.string.character_detail_species_label),
        value = character.species,
        labelTextStyle = labelTextStyle,
        valueTextStyle = valueTextStyle,
    )
    Spacer(modifier = Modifier.height(spacing))
    DetailItem(
        labelString = stringResource(R.string.character_detail_gender_label),
        value = character.gender.toString(),
        labelTextStyle = labelTextStyle,
        valueTextStyle = valueTextStyle,
    )
    Spacer(modifier = Modifier.height(spacing))
    DetailItem(
        labelString = stringResource(R.string.character_detail_origin_label),
        value = character.origin,
        labelTextStyle = labelTextStyle,
        valueTextStyle = valueTextStyle,
    )
    Spacer(modifier = Modifier.height(spacing))
    DetailItem(
        labelString = stringResource(R.string.character_detail_location_label),
        value = character.location,
        labelTextStyle = labelTextStyle,
        valueTextStyle = valueTextStyle,
    )
    Spacer(modifier = Modifier.height(spacing))
    DetailItem(
        labelString = stringResource(R.string.character_detail_episodes_label),
        value = character.episode.size.toString(),
        labelTextStyle = labelTextStyle,
        valueTextStyle = valueTextStyle,
    )
}

/**
 * Displays a label-value pair.
 *
 * @param labelString The string resource for the label.
 * @param value The string value to display.
 * @param labelTextStyle [TextStyle] for the label.
 * @param valueTextStyle [TextStyle] for the value.
 */
@Composable
private fun DetailItem(
    labelString: String,
    value: String,
    labelTextStyle: TextStyle,
    valueTextStyle: TextStyle,
) {
    Text(
        text = labelString,
        style = labelTextStyle
    )
    Text(
        text = value,
        style = valueTextStyle
    )
}

@Preview(showBackground = true)
@Composable
fun DetailViewPreview() {
    ProvidePreview {
        DetailView(character = previewCharacter)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailViewPreviewDark() {
    ProvidePreview {
        DetailView(character = previewCharacter)
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 600)
@Composable
fun DetailViewPreviewWide() {
    ProvidePreview {
        DetailView(character = previewCharacter)
    }
}

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 600,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DetailViewPreviewWideDark() {
    ProvidePreview {
        DetailView(character = previewCharacter)
    }
}
