package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.preview.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.feature.characters.R

/**
 * Composable function for the header of the detail screen.
 * It displays the character's name and a back button, with elevation.
 *
 * @param characterName The name of the character to display in the header.
 * @param modifier Modifier for this composable.
 * @param onBackClick Callback invoked when the back button is clicked.
 */
@Composable
fun DetailHeader(
    characterName: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.small_space)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = characterName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailHeaderPreview() {
    ProvidePreview {
        DetailHeader(characterName = "Rick Sanchez", onBackClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun DetailHeaderDarkPreview() {
    ProvidePreview {
        DetailHeader(characterName = "Morty Smith", onBackClick = {})
    }
}
