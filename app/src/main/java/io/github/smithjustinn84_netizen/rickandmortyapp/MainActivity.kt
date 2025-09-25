package io.github.smithjustinn84_netizen.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.theme.RickAndMortyTheme

/**
 * The main activity for the Rick and Morty application.
 * This activity serves as the entry point and hosts the [NavGraph].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is first created.
     * This is where you should do all of your normal static set up: create views, bind data to lists, etc.
     * This method also calls [enableEdgeToEdge] to enable edge-to-edge display and sets the content to [RickAndMortyTheme] wrapping the [NavGraph].
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                NavGraph()
            }
        }
    }
}
