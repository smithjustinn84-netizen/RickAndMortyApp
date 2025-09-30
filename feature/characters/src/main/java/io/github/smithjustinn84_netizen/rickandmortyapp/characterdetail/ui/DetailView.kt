package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.Character
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model.previewCharacter
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.preview.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.preview.previewHandler
import io.github.smithjustinn84_netizen.rickandmortyapp.feature.characters.R
import io.github.smithjustinn84_netizen.rickandmortyapp.feature.characters.R.dimen.detail_item_spacing
import io.github.smithjustinn84_netizen.rickandmortyapp.feature.characters.R.dimen.detail_padding

@Composable
fun Details(
    character: Character,
    textAlign: TextAlign,
    labelTextStyle: TextStyle,
    valueTextStyle: TextStyle,
    spacing: Dp,
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
        )
        DetailItem(
            label = stringResource(R.string.gender),
            value = character.gender.toString(),
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
        )
        DetailItem(
            label = stringResource(R.string.origin),
            value = character.origin,
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
        )
        DetailItem(
            label = stringResource(R.string.location),
            value = character.location,
            textAlign = textAlign,
            labelTextStyle = labelTextStyle,
            valueTextStyle = valueTextStyle,
            spacing = spacing,
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
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label:", style = labelTextStyle)
        Spacer(modifier = Modifier.width(spacing))
        Text(text = value, style = valueTextStyle, textAlign = textAlign)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CharacterImage(
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
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(dimensionResource(R.dimen.detail_item_corner_radius))),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(name = "Detail View - Light Mode", showBackground = true, widthDp = 360)
@Composable
fun DetailViewLightPreview() {
    ProvidePreview {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(detail_padding)),
        ) {
            item {
                DetailHeader(
                    characterName = previewCharacter.name
                )
            }

            item {
                Text(
                    text = previewCharacter.name,
                    style = MaterialTheme.typography.displayMedium,
                )
                Spacer(modifier = Modifier.height(dimensionResource(detail_item_spacing)))
            }

            item {
                CharacterImage(
                    imageUrl = previewCharacter.image,
                    characterName = previewCharacter.name,
                )
                Spacer(modifier = Modifier.height(dimensionResource(detail_item_spacing)))
            }

            item {
                Details(
                    character = previewCharacter,
                    textAlign = TextAlign.Start,
                    labelTextStyle = MaterialTheme.typography.labelMedium,
                    valueTextStyle = MaterialTheme.typography.titleMedium,
                    spacing = dimensionResource(detail_item_spacing),
                )
            }
        }
    }
}

@Preview(
    name = "Detail View - Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 360
)
@Composable
fun DetailViewDarkPreview() {
    ProvidePreview {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(detail_padding)),
        ) {
            item {
                DetailHeader(
                    characterName = previewCharacter.name
                )
            }

            item {
                Text(
                    text = previewCharacter.name,
                    style = MaterialTheme.typography.displayMedium,
                )
                Spacer(modifier = Modifier.height(dimensionResource(detail_item_spacing)))
            }

            item {
                CharacterImage(
                    imageUrl = previewCharacter.image,
                    characterName = previewCharacter.name,
                )
                Spacer(modifier = Modifier.height(dimensionResource(detail_item_spacing)))
            }

            item {
                Details(
                    character = previewCharacter,
                    textAlign = TextAlign.Start,
                    labelTextStyle = MaterialTheme.typography.labelMedium,
                    valueTextStyle = MaterialTheme.typography.titleMedium,
                    spacing = dimensionResource(detail_item_spacing),
                )
            }
        }
    }
}
