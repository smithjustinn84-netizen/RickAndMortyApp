package io.github.smithjustinn84_netizen.rickandmortyapp.characterdetail.model

import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.Gender
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.Status

/**
 * A single sample [Character] object for use in previews and testing.
 */
val previewCharacter = Character(
    id = 1,
    name = "Rick Sanchez",
    status = Status.ALIVE,
    species = "Human",
    gender = Gender.MALE,
    origin = "Earth (C-137)",
    location = "Citadel of Ricks",
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    episode = listOf(
        "https://rickandmortyapi.com/api/episode/1",
        "https://rickandmortyapi.com/api/episode/2"
    )
)
