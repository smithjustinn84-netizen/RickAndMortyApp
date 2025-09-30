package io.github.smithjustinn84_netizen.rickandmortyapp.characters.model

import androidx.compose.ui.graphics.Color

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

    val color: Color
        get() = when (this) {
            UNKNOWN -> Color.Gray
            ALIVE -> Color.Green
            DEAD -> Color.Red
        }
}
