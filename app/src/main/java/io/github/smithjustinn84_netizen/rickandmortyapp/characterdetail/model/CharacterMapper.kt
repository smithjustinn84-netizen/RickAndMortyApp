package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model

import io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character as DomainCharacter
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.model.Gender
import io.github.smithjustinn84_netizen.rickandmortyapp.ui.model.Status

/**
 * Maps a [DomainCharacter] from the domain layer to a [Character] in the presentation layer.
 */
fun DomainCharacter.toUi(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = Status.valueOf(this.status.uppercase()),
        species = this.species,
        gender = Gender.valueOf(this.gender.uppercase()),
        origin = this.origin,
        location = this.location,
        episode = this.episode,
        image = this.image
    )
}