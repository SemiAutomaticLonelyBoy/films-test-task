package com.example.filmstest.data.dataSource

import com.example.filmstest.data.models.FilmsResponse
import retrofit2.http.GET

interface FilmsDataSource {

    @GET("sequeniatesttask/films.json")
    suspend fun getFilms(): FilmsResponse
}
