package com.example.filmstest.presentation.ui.features.filmInfo

import androidx.compose.runtime.Immutable
import com.example.filmstest.presentation.ui.features.filmsList.FilmState

@Immutable
data class FilmInfoScreenState(
    val film: FilmState? = null,
)