package io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.theme.RickAndMortyTheme

@Composable
fun ProvidePreview(content: @Composable () -> Unit) {
    RickAndMortyTheme {
        Surface {
            content()
        }
    }
}
