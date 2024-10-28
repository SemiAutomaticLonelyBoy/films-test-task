package com.example.filmstest.domain.usecase

import com.example.filmstest.domain.etities.FilmEntity

interface GetFilmsUseCase {

    suspend operator fun invoke(): List<FilmEntity>
}