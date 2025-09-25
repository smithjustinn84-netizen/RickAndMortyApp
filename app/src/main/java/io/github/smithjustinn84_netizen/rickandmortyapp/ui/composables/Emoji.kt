package io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * A composable function that displays a single emoji character centered within a Box.
 *
 * This composable is useful for rendering emojis with a consistent size and styling.
 *
 * @param modifier Modifier to be applied to the layout box. Defaults to [Modifier].
 * @param emoji The string representation of the emoji to be displayed.
 */
@Composable
fun Emoji(modifier: Modifier = Modifier, emoji: String) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmojiPreview() {
    MaterialTheme {
        Emoji(emoji = "🎉")
    }
}
