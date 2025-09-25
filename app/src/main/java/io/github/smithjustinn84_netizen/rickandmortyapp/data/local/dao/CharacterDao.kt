package io.github.smithjustinn84_netizen.rickandmortyapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.CharacterEntity

/**
 * Data Access Object (DAO) for the `character` table.
 */
@Dao
interface CharacterDao {
    /**
     * Inserts or updates a list of [CharacterEntity] in the database.
     * If a character already exists, it will be updated; otherwise, it will be inserted.
     *
     * @param characters The list of characters to insert or update.
     */
    @Upsert
    suspend fun insertAll(characters: List<CharacterEntity>)

    /**
     * Returns a [PagingSource] for all characters in the database.
     *
     * @return A [PagingSource] for [CharacterEntity] objects.
     */
    @Query("SELECT * FROM character")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    /**
     * Deletes all characters from the database.
     */
    @Query("DELETE FROM character")
    suspend fun clearAll()

    /**
     * Retrieves a specific character by its ID.
     *
     * @param id The ID of the character to retrieve.
     * @return The [CharacterEntity] with the given ID.
     */
    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacter(id: Int): CharacterEntity

    /**
     * Returns the total number of characters in the database.
     *
     * @return The count of characters.
     */
    @Query("SELECT COUNT(*) FROM character")
    suspend fun getCharacterCount(): Int

    /**
     * Checks if the character table is empty.
     * This is a default interface method that utilizes [getCharacterCount].
     *
     * @return `true` if the table is empty, `false` otherwise.
     */
    suspend fun isEmpty(): Boolean {
        return getCharacterCount() == 0
    }
}