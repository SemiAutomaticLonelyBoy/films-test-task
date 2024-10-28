package com.example.filmstest.di

import android.content.Context
import com.example.filmstest.data.dataSource.FilmsDataSource
import com.example.filmstest.data.repositories.FilmsRepositoryImpl
import com.example.filmstest.domain.repositories.FilmsRepository
import com.example.filmstest.domain.usecase.GetFilmsUseCase
import com.example.filmstest.domain.usecase.GetFilmsUseCaseImpl
import com.example.filmstest.presentation.ui.features.filmInfo.FilmInfoViewModel
import com.example.filmstest.presentation.ui.features.filmsList.FilmsListViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val modules = module {
    // viewModels
    viewModelOf(::FilmsListViewModel)
    viewModelOf(::FilmInfoViewModel)

    // useCase
    factory<GetFilmsUseCase> { GetFilmsUseCaseImpl(get()) }

    // repository
    factory<FilmsRepository> { FilmsRepositoryImpl(get()) }

    // retrofit instances
    val url: String = "filmsUrl"
    single(named(url)) {
        provideCustomRetrofit(
            androidContext(), "https://s3-eu-west-1.amazonaws.com/"
        )
    }

    // dataSource
    single { get<Retrofit>(named(url)).create(FilmsDataSource::class.java) } bind FilmsDataSource::class

}

private fun provideCustomRetrofit(context: Context, url: String): Retrofit {
    val networkJson = Json { ignoreUnknownKeys = true }

    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(networkJson.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .build()
}
