package io.github.smithjustinn84_netizen.rickandmortyapp.characters.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables.LogoImage
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables.ProvidePreview
import io.github.smithjustinn84_netizen.rickandmortyapp.R

/**
 * Composable function that displays the header of the character list screen.
 */
@Composable
fun CharacterHeader(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.header_padding)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LogoImage()
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.small_space))) // Added spacer
            Text(
                text = "Characters",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterHeaderPreview() {
    ProvidePreview {
        CharacterHeader()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun CharacterHeaderDarkPreview() {
    ProvidePreview {
        CharacterHeader()
    }
}
