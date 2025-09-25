package io.github.smithjustinn84_netizen.rickandmortyapp.data

import androidx.paging.Pager
import androidx.paging.PagingData
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.dao.CharacterDao
import io.github.smithjustinn84_netizen.rickandmortyapp.data.local.model.CharacterEntity
import io.github.smithjustinn84_netizen.rickandmortyapp.data.mappers.toDomain
import io.github.smithjustinn84_netizen.rickandmortyapp.domain.CharacterRepo
import io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character
import io.github.smithjustinn84_netizen.rickandmortyapp.extensions.mapPagingDataItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [io.github.smithjustinn84_netizen.rickandmortyapp.domain.CharacterRepo] that uses a [androidx.paging.Pager] and [CharacterDao].
 *
 * @property pager The [androidx.paging.Pager] for [CharacterEntity] objects.
 * @property dao The [CharacterDao] for accessing local character data.
 */
@Singleton
class CharacterRepoImpl @Inject constructor(
    private val pager: Pager<Int, CharacterEntity>,
    private val dao: CharacterDao
) : CharacterRepo {

    /**
     * Returns a [kotlinx.coroutines.flow.Flow] of [androidx.paging.PagingData] for [io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character] domain objects.
     * The data is fetched from the [pager] and mapped to [io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character] domain objects.
     *
     * @return A [kotlinx.coroutines.flow.Flow] of [androidx.paging.PagingData] containing [Character] domain objects.
     */
    override fun getCharacters(): Flow<PagingData<Character>> {
        // Map from CharacterEntity to domain Character
        return pager.flow.mapPagingDataItems { it.toDomain() }
    }

    /**
     * Suspended function to get a specific character by its ID from the local database.
     *
     * @param id The ID of the character to retrieve.
     * @return The [Character] domain object corresponding to the given ID.
     */
    override suspend fun getCharacter(id: Int): Character {
        // Map from CharacterEntity to domain Character
        return dao.getCharacter(id).toDomain()
    }
}
