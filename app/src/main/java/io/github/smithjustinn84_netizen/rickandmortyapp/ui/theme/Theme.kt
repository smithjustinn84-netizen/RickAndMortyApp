package io.github.smithjustinn84_netizen.rickandmortyapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Defines the dark color scheme for the application
 */
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

/**
 * Defines the light color scheme for the application
 */
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

/**
 * The main theme for the RickAndMorty application.
 *
 * This composable function applies the appropriate color scheme (light or dark)
 * based on the system settings or the [darkTheme] parameter. It also applies
 * the custom [Typography] defined for the application.
 *
 * @param darkTheme Whether to use the dark color scheme. Defaults to the system's dark theme setting.
 * @param content The composable content to which this theme will be applied.
 */
@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
