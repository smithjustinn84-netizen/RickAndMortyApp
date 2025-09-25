package io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.model.Status
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.theme.RickAndMortyTheme

/**
 * Composable function that displays an icon representing the character's status.
 *
 * @param status The status string of the character (e.g., "Alive", "Dead", "unknown").
 * @param modifier Modifier for this composable.
 */
@Composable
fun StatusIcon(status: Status, modifier: Modifier = Modifier) {
    when (status) {
        Status.ALIVE -> Emoji(modifier, "🟢")
        Status.DEAD -> Emoji(modifier, "☠️")
        Status.UNKNOWN -> Emoji(modifier, "❓")
    }
}

@Composable
private fun StatusIconPreviewBase(status: Status, darkTheme: Boolean = false) {
    RickAndMortyTheme(darkTheme = darkTheme) {
        Surface {
            StatusIcon(status)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatusIconPreview() {
    StatusIconPreviewBase(status = Status.ALIVE)
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatusIconPreviewDark() {
    StatusIconPreviewBase(status = Status.ALIVE, darkTheme = true)
}

@Preview(showBackground = true)
@Composable
fun StatusIconPreviewDead() {
    StatusIconPreviewBase(status = Status.DEAD)
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatusIconPreviewDeadDark() {
    StatusIconPreviewBase(status = Status.DEAD, darkTheme = true)
}

@Preview(showBackground = true)
@Composable
fun StatusIconPreviewUnknown() {
    StatusIconPreviewBase(status = Status.UNKNOWN)
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatusIconPreviewUnknownDark() {
    StatusIconPreviewBase(status = Status.UNKNOWN, darkTheme = true)
}
