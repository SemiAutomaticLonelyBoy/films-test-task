package com.example.filmstest.presentation.ui.features.filmInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmstest.domain.etities.FilmEntity
import com.example.filmstest.presentation.ui.features.filmsList.FilmsListViewModel
import com.example.filmstest.presentation.ui.features.filmsList.toState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FilmInfoViewModel() : ViewModel() {

    private val mutableState: MutableStateFlow<FilmInfoScreenState> = MutableStateFlow(FilmInfoScreenState())
    val state: StateFlow<FilmInfoScreenState> = mutableState.asStateFlow()

    private val mutableActions: Channel<OutputAction> = Channel()
    val action: Flow<OutputAction> = mutableActions.receiveAsFlow()

    fun inputAction(action: InputAction) {
        when(action) {
            InputAction.OnBackClick -> navigateToFilmsListScreen()
            is InputAction.OnStart -> setFilm(action.film)
        }
    }

    private fun navigateToFilmsListScreen() {
        viewModelScope.launch {
            mutableActions.send(OutputAction.GoBack)
        }
    }

    private fun setFilm(film: FilmEntity) {
        mutableState.value = state.value.copy(
            film = film.toState(),
        )
    }

    sealed class OutputAction {
        data object GoBack : OutputAction()
    }

    sealed class InputAction {
        data class OnStart(val film: FilmEntity) : InputAction()
        data object OnBackClick : InputAction()
    }

}