package io.github.smithjustinn84_netizen.rickandmortyapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import javax.inject.Inject

/**
 * Network data source for fetching character data from the Rick and Morty API.
 *
 * @property client The Ktor HTTP client for making network requests.
 */
class CharacterNetworkDataSource @Inject constructor(
    private val client: HttpClient
) : NetworkDataSource {

    /**
     * Loads a list of characters for a given page number.
     *
     * @param page The page number to load.
     * @return An [ApiCharacters] object containing the list of characters and pagination info.
     */
    override suspend fun loadCharacters(page: Int): ApiCharacters {
        return client.get(Character(page)).body()
    }
}

/**
 * Represents the /character API endpoint.
 *
 * @property page The page number to request.
 */
@Resource("/character")
private data class Character(val page: Int)
