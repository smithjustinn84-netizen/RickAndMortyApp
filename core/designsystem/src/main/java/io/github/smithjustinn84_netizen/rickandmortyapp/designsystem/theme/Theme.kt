package io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = White,
    secondary = DarkGray,
    onSecondary = White,
    tertiary = Green,
    onTertiary = White,
    background = Black,
    surface = DarkGray,
    error = ErrorColor,
    onError = White,
    onBackground = White,
    onSurface = White,
    onSurfaceVariant = LightGray
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = White,
    secondary = LightGray,
    onSecondary = Black,
    tertiary = Green,
    onTertiary = White,
    background = White,
    surface = OffWhite,
    error = ErrorColor,
    onError = White,
    onBackground = Black,
    onSurface = Black,
    onSurfaceVariant = Black
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
