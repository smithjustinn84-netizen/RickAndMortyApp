package io.github.smithjustinn84_netizen.rickandmortyapp.characters.model

enum class Status {
    UNKNOWN,
    ALIVE,
    DEAD;

    override fun toString(): String {
        return when (this) {
            UNKNOWN -> "Unknown"
            ALIVE -> "Alive"
            DEAD -> "Dead"
        }
    }
}
