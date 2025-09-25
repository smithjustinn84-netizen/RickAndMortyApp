package io.github.smithjustinn84_netizen.rickandmortyapp.characters.model

import io.github.smithjustinn84_netizen.rickandmortyapp.ui.model.Status


/**
 * Represents a character in the UI layer.
 *
 * @property id The unique identifier of the character.
 * @property name The name of the character.
 * @property status The status of the character (e.g., "Alive", "Dead").
 * @property species The species of the character.
 * @property image The URL of the character's image.
 */
data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val image: String,
)
