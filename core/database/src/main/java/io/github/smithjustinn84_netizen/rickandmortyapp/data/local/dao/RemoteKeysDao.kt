package io.github.smithjustinn84_netizen.rickandmortyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.RemoteKeys

/**
 * Data Access Object (DAO) for [RemoteKeys] entities.
 *
 * This interface provides methods for interacting with the `remote_keys` table in the database,
 * primarily for supporting Paging 3 with a [androidx.paging.RemoteMediator].
 */
@Dao
interface RemoteKeysDao {

    /**
     * Inserts a list of [RemoteKeys] into the database.
     * If a [RemoteKeys] object with the same primary key already exists, it will be replaced.
     *
     * @param remoteKey The list of [RemoteKeys] to insert.
     */
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    /**
     * Retrieves the [RemoteKeys] for a specific character ID.
     *
     * @param characterId The ID of the character for which to retrieve the [RemoteKeys].
     * @return The [RemoteKeys] object if found, otherwise null.
     */
    @Query("SELECT * FROM remote_keys WHERE characterId = :characterId")
    suspend fun remoteKeysCharacterId(characterId: Int): RemoteKeys?

    /**
     * Deletes all [RemoteKeys] from the `remote_keys` table.
     */
    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT MAX(created_at) FROM remote_keys")
    suspend fun getLatestCreationTime(): Long?
}
