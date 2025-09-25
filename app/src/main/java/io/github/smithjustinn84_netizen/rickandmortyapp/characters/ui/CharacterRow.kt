package io.github.smithjustinn84_netizen.rickandmortyapp.characters.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.previewCharacter
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables.StatusIcon
import io.github.smithjustinn84_netizen.rickandmortyapp.R
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.Character

/**
 * Composable function that displays a single character row in the list.
 *
 * @param character The [io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.Character] data for the row.
 * @param onClick Callback invoked when the character row is clicked, providing the character's ID.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun CharacterRow(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    ListItem(
        headlineContent = {
            Text(
                character.name,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        supportingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusIcon(character.status)
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.small_space)))
                Text(
                    text = "${character.status} - ${character.species}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        leadingContent = {
            CharacterImage(
                character,
                modifier = Modifier.clip(RoundedCornerShape(25.dp))
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(R.dimen.vertical_margin),
                horizontal = dimensionResource(R.dimen.horizontal_margin)
            )
            .clickable { onClick(character.id) }
    )
}

@Preview(showBackground = true)
@Composable
fun CharacterRowPreview() {
    ProvidePreview {
        CharacterRow(character = previewCharacter)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterRowDarkPreview() {
    ProvidePreview {
        CharacterRow(character = previewCharacter)
    }
}
