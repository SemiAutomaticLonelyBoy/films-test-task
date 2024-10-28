package com.example.filmstest.presentation.ui.features.filmsList

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmstest.R
import com.example.filmstest.domain.etities.FilmEntity
import com.example.filmstest.domain.usecase.GetFilmsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FilmsListViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
) : ViewModel() {

    private val mutableState: MutableStateFlow<FilmsListScreenState> =
        MutableStateFlow(FilmsListScreenState())
    val state: StateFlow<FilmsListScreenState> = mutableState.asStateFlow()

    private val mutableActions: Channel<OutputAction> = Channel()
    val action: Flow<OutputAction> = mutableActions.receiveAsFlow()

    init {
        getFilms()
    }

    fun inputAction(action: InputAction) {
        when (action) {
            is InputAction.SetGenre -> setGenre(action.genre)
            InputAction.OnStart -> getFilms()
            is InputAction.OnFilmCardClick -> navigateToFilmInfoScreen(action.film)
        }
    }

    private fun navigateToFilmInfoScreen(film: FilmState) {
        viewModelScope.launch {
            mutableActions.send(
                OutputAction.GoToFilmInfoScreen(film.toEntity())
            )
        }
    }

    private fun setGenre(genre: String) {
        val success = (state.value.screenState as? FilmsListState.Content) ?: return

        mutableState.value = state.value.copy(
            screenState = success.copy(
                selectedGenre = if (genre == success.selectedGenre) null else genre,
            ),
        )
    }

    private fun getFilms() {
        mutableState.value = FilmsListScreenState(
            screenState = FilmsListState.Loading,
        )
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getFilmsUseCase()
            }.onSuccess { films ->
                mutableState.value = FilmsListScreenState(
                    screenState = films.toScreenState()
                )
            }.onFailure {
                showError()
            }
        }
    }

    private suspend fun showError() {
        mutableState.value = FilmsListScreenState(
            screenState = FilmsListState.Error
        )

        mutableActions.send(
            OutputAction.ShowError(
                message = R.string.snackbar_message,
                textButton = R.string.snackbar_text_buton,
                onRefresh = {
                    getFilms()
                },
            )
        )
    }

    sealed class OutputAction {
        data class ShowError(
            val message: Int,
            val textButton: Int,
            val onRefresh: () -> Unit,
        ) : OutputAction()

        data class GoToFilmInfoScreen(val film: FilmEntity) : OutputAction()
    }

    sealed class InputAction {
        data object OnStart : InputAction()
        data class SetGenre(val genre: String) : InputAction()
        data class OnFilmCardClick(val film: FilmState) : InputAction()
    }
}
