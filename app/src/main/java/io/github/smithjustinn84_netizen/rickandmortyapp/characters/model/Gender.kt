package io.github.smithjustinn84_netizen.rickandmortyapp.characters.model

enum class Gender {
    UNKNOWN,
    FEMALE,
    MALE,
    GENDERLESS;

    override fun toString(): String {
        return when (this) {
            UNKNOWN -> "Unknown"
            FEMALE -> "Female"
            MALE -> "Male"
            GENDERLESS -> "Genderless"
        }
    }
}
