package io.github.smithjustinn84_netizen.rickandmortyapp.data.remote

import kotlinx.serialization.Serializable

/**
 * Represents a character from the Rick and Morty API.
 *
 * @property id The ID of the character.
 * @property name The name of the character.
 * @property status The status of the character (e.g., "Alive", "Dead", "unknown").
 * @property species The species of the character.
 * @property type The type or subspecies of the character.
 * @property gender The gender of the character (e.g., "Female", "Male", "Genderless", "unknown").
 * @property origin The origin location of the character.
 * @property location The last known location of the character.
 * @property image Link to the character's image.
 * @property episode List of URLs of episodes in which this character appeared.
 * @property url Link to the character's own URL endpoint.
 * @property created Time at which the character was created in the database.
 */
@Serializable
data class ApiCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

/**
 * Represents the origin of a character.
 *
 * @property name The name of the location.
 * @property url Link to the location's own URL endpoint.
 */
@Serializable
data class Origin(
    val name: String,
    val url: String
)

/**
 * Represents the last known location of a character.
 *
 * @property name The name of the location.
 * @property url Link to the location's own URL endpoint.
 */
@Serializable
data class Location(
    val name: String,
    val url: String
)

/**
 * Represents pagination information from the API.
 *
 * @property next Link to the next page of results, if available.
 * @property prev Link to the previous page of results, if available.
 */
@Serializable
data class Info(
    val next: String? = null,
    val prev: String? = null
)

/**
 * Represents a response from the API when requesting a list of characters.
 *
 * @property info Pagination information.
 * @property results List of [ApiCharacter] objects.
 */
@Serializable
data class ApiCharacters(
    val info: Info,
    val results: List<ApiCharacter>
)
