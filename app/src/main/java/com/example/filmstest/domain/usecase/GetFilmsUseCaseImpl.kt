package com.example.filmstest.domain.usecase

import com.example.filmstest.domain.etities.FilmEntity
import com.example.filmstest.domain.repositories.FilmsRepository
import com.example.filmstest.data.models.toEntity

class GetFilmsUseCaseImpl(
    private val repository: FilmsRepository,
) : GetFilmsUseCase {

    override suspend fun invoke(): List<FilmEntity> = repository
        .getFilms().films.map { it.toEntity() }
}
