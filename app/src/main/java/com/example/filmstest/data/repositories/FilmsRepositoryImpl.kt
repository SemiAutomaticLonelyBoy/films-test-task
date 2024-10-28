package com.example.filmstest.data.repositories

import com.example.filmstest.data.dataSource.FilmsDataSource
import com.example.filmstest.data.models.FilmsResponse
import com.example.filmstest.domain.repositories.FilmsRepository

class FilmsRepositoryImpl(
    private val filmsDataSource: FilmsDataSource,
) : FilmsRepository {

    override suspend fun getFilms(): FilmsResponse = filmsDataSource
        .getFilms()
}
