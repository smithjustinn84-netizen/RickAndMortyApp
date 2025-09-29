package io.github.smithjustinn84_netizen.rickandmortyapp.data.mappers

import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.CharacterEntity
import io.github.smithjustinn84_netizen.rickandmortyapp.network.ApiCharacter
import io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character

/**
 * Maps a [CharacterEntity] from the data layer to a [Character] in the domain layer.
 */
fun CharacterEntity.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        origin = this.origin,
        image = this.image,
        location = this.location,
        episode = this.episode
    )
}

/**
 * Converts a [ApiCharacter] to a [CharacterEntity] model.
 *
 * @receiver The [ApiCharacter] to convert.
 * @return The corresponding [CharacterEntity] model.
 */
fun ApiCharacter.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin.name,
        image = image,
        location = location.name,
        episode = episode
    )
}

/**
 * Converts a list of [ApiCharacter] objects to a list of [CharacterEntity] models.
 *
 * @receiver The list of [ApiCharacter] objects to convert.
 * @return The corresponding list of [CharacterEntity] models.
 */
fun List<ApiCharacter>.toEntities(): List<CharacterEntity> =
    map { it.toEntity() }
