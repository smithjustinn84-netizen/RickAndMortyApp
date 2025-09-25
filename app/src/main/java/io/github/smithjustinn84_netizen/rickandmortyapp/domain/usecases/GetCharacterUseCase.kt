package io.github.smithjustinn84_netizen.rickandmortyapp.domain.usecases

import io.github.smithjustinn84_netizen.rickandmortyapp.domain.CharacterRepo
import io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character
import javax.inject.Inject

/**
 * Use case for fetching a single character.
 *
 * This use case retrieves a character by its ID from the [CharacterRepo]
 * and maps it to a UI model.
 *
 * @property characterRepo The repository for accessing character data.
 */
class GetCharacterUseCase @Inject constructor(
    private val characterRepo: CharacterRepo
) {
    /**
     * Fetches a character by its ID.
     *
     * @param id The ID of the character to fetch.
     * @return The character details as [io.github.smithjustinn84_netizen.rickandmortyapp.domain.model.Character].
     * @throws Exception if the character fetch fails.
     */
    suspend operator fun invoke(id: Int): Character {
        return characterRepo.getCharacter(id)
    }
}
