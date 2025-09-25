package io.github.smithjustinn84_netizen.rickandmortyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.dao.CharacterDao
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.dao.RemoteKeysDao
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.CharacterEntity
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.RemoteKeys

/**
 * The Room database for this app.
 *
 * This database stores [io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.CharacterEntity] and [io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.RemoteKeys] entities.
 * Version: 1
 * Export Schema: false
 */
@Database(entities = [CharacterEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Returns the Data Access Object for the [RemoteKeys] table.
     */
    abstract fun remoteKeysDao(): RemoteKeysDao

    /**
     * Returns the Data Access Object for the [CharacterEntity] table.
     */
    abstract fun characterDao(): CharacterDao
}