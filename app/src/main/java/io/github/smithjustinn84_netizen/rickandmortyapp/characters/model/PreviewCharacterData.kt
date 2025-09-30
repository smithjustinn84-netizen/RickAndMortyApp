package io.github.smithjustinn84_netizen.rickandmortyapp.characters.model

/**
 * A list of preview characters for UI development and testing.
 */
val previewCharacters = listOf(
    Character(
        id = 1,
        name = "Rick Sanchez",
        status = Status.ALIVE,
        species = "Human",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    ),
    Character(
        id = 2,
        name = "Morty Smith",
        status = Status.ALIVE,
        species = "Human",
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
    ),
    Character(
        id = 3,
        name = "Summer Smith",
        status = Status.ALIVE,
        species = "Human",
        image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
    ),
    Character(
        id = 4,
        name = "Beth Smith",
        status = Status.ALIVE,
        species = "Human",
        image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg"
    ),
)

/**
 * A single preview character for UI development and testing.
 */
val previewCharacter = Character(
    id = 1,
    name = "Rick Sanchez",
    status = Status.ALIVE,
    species = "Human",
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
)
