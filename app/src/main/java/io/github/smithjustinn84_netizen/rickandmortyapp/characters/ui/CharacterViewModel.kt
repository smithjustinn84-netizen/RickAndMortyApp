package io.github.smithjustinn84_netizen.rickandmortyapp.characters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.Character
import io.github.smithjustinn84_netizen.rickandmortyapp.characters.model.toUi
import io.github.smithjustinn84_netizen.rickandmortyapp.domain.usecases.GetCharactersUseCase
import io.github.smithjustinn84_netizen.rickandmortyapp.data.paging.mapPagingDataItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * ViewModel for the character list screen.
 *
 * This ViewModel is responsible for fetching character data using the [GetCharactersUseCase],
 * mapping it to UI-specific models ([Character]), and providing it as a
 * [Flow] of [PagingData] to the UI.
 *
 * @property getCharactersUseCase The use case for accessing character domain models.
 */
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {
    /**
     * A [Flow] of [PagingData] for [Character] objects, ready for the UI.
     * The data is fetched from the use case, mapped to UI models, and cached in the [viewModelScope].
     */
    val pager: Flow<PagingData<Character>> = getCharactersUseCase()
        .mapPagingDataItems {
            domainCharacter -> domainCharacter.toUi()
        }
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}
