package io.github.smithjustinn84_netizen.rickandmortyapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character

/**
 * Interface for accessing character data.
 */
interface CharacterRepo {
    /**
     * Returns a [kotlinx.coroutines.flow.Flow] of [androidx.paging.PagingData] for [io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character] domain objects.
     *
     * @return A [kotlinx.coroutines.flow.Flow] of [androidx.paging.PagingData] containing [io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character] domain objects.
     */
    fun getCharacters(): Flow<PagingData<Character>>

    /**
     * Suspended function to get a specific character by its ID.
     *
     * @param id The ID of the character to retrieve.
     * @return The [Character] domain object corresponding to the given ID.
     */
    suspend fun getCharacter(id: Int): Character
}
