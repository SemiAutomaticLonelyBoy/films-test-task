package com.example.filmstest.domain.repositories

import com.example.filmstest.data.models.FilmsResponse

interface FilmsRepository {
    suspend fun getFilms() : FilmsResponse
}