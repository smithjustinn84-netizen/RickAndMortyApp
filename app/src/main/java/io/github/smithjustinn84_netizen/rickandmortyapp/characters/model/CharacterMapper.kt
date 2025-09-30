package io.github.smithjustinn84_netizen.rickandmortyapp.characters.model

import io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character as DomainCharacter

/**
 * Converts a [DomainCharacter] to a UI model [Character].
 *
 * @return The UI model [Character].
 */
fun DomainCharacter.toUi(): Character {
    return Character(
        id = id,
        name = name,
        status = Status.valueOf(status.uppercase()),
        species = species,
        image = image,
    )
}