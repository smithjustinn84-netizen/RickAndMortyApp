package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.ui

import io.github.smithjustinn84_netizen.rickandmortyapp.feature.characters.R
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
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.preview.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.preview.previewHandler

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

@Composable
fun Details(
    character: Character,
    textAlign: TextAlign,
    labelTextStyle: TextStyle,
    valueTextStyle: TextStyle,
    spacing: Dp,
    isItemLayoutHorizontal: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        DetailItem(
            label = stringResource(R.string.species),
            value = character.species,
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
            isHorizontal = isItemLayoutHorizontal
        )
        DetailItem(
            label = stringResource(R.string.gender),
            value = character.gender.toString(),
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
            isHorizontal = isItemLayoutHorizontal
        )
        DetailItem(
            label = stringResource(R.string.status),
            value = character.status.toString(),
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
            isHorizontal = isItemLayoutHorizontal
        )
        DetailItem(
            label = stringResource(R.string.origin),
            value = character.origin,
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
            isHorizontal = isItemLayoutHorizontal
        )
        DetailItem(
            label = stringResource(R.string.location),
            value = character.location,
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
            isHorizontal = isItemLayoutHorizontal
        )
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    textAlign: TextAlign,
    labelTextStyle: TextStyle,
    valueTextStyle: TextStyle,
    spacing: Dp,
    isHorizontal: Boolean,
) {
    if (isHorizontal) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$label:", style = labelTextStyle)
            Spacer(modifier = Modifier.width(spacing))
            Text(text = value, style = valueTextStyle, textAlign = textAlign)
        }
    } else {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "$label:", style = labelTextStyle)
            Spacer(modifier = Modifier.height(spacing))
            Text(text = value, style = valueTextStyle, textAlign = textAlign)
            Spacer(modifier = Modifier.height(spacing))
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun CharacterImage(
    imageUrl: String,
    characterName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = characterName,
            modifier = modifier
                .clip(RoundedCornerShape(dimensionResource(R.dimen.detail_item_corner_radius))),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(name = "Detail View - Light Mode", showBackground = true, widthDp = 360)
@Composable
fun DetailViewLightPreview() {
    ProvidePreview {
        DetailView(character = previewCharacter)
    }
}

@Preview(name = "Detail View - Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 360)
@Composable
fun DetailViewDarkPreview() {
    ProvidePreview {
        DetailView(character = previewCharacter)
    }
}
