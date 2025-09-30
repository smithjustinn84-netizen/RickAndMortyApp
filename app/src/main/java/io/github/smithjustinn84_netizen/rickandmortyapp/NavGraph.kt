package io.github.smithjustinn84_netizen.rickandmortyapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.ui.DetailScreen
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.ui.CharacterScreen
import kotlinx.serialization.Serializable

/**
 * Composable function that defines the navigation graph for the Rick and Morty application.
 *
 * @param modifier Modifier to be applied to the NavHost.
 * @param navController The [NavHostController] used for navigation.
 * @param startDestination The starting destination for the navigation graph.
 */
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = CharacterRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<CharacterRoute> {
            CharacterScreen(
                onClick = { characterId ->
                    navController.navigate(DetailRoute(characterId))
                }
            )
        }
        composable<DetailRoute> {
            DetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

/**
 * Represents a navigation destination in the application.
 * Declared as a sealed interface to enable exhaustiveness checks
 * when used in `when` expressions, improving type safety.
 */
sealed interface Destination

/**
 * Represents the navigation route for the character list screen.
 */
@Serializable
object CharacterRoute : Destination

/**
 * Represents the navigation route for the character detail screen.
 *
 * @property characterId The ID of the character to display.
 */
@Serializable
data class DetailRoute(val characterId: Int) : Destination
