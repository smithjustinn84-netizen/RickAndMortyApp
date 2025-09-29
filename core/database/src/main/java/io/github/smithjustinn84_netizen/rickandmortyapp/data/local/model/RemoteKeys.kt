package io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents remote keys for pagination in the database.
 * This entity is used by [androidx.paging.RemoteMediator] to keep track of the
 * previous and next page keys for items loaded from a remote data source.
 *
 * @property characterId The ID of the character this remote key entry is associated with. Serves as the primary key.
 * @property prevKey The key for the previous page of data, or null if there is no previous page.
 * @property nextKey The key for the next page of data, or null if there is no next page (end of list).
 * @property createdAt The timestamp (in milliseconds) when this remote key entry was created.
 *                   Used to determine cache freshness and potentially invalidate old data.
 */
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
