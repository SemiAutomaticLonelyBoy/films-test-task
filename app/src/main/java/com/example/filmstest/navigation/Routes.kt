package com.example.filmstest.navigation

import com.example.filmstest.domain.etities.FilmEntity
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object FilmsList

    @Serializable
    data class FilmInfo(
        val film: FilmEntity,
    )
}