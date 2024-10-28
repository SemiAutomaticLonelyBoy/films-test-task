package com.example.filmstest.presentation.ui.features.filmsList

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.filmstest.domain.etities.FilmEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.util.Locale

@Immutable
data class FilmsListScreenState(
    val screenState: FilmsListState = FilmsListState.Loading,
)

@Immutable
sealed class FilmsListState {
    @Immutable
    data class Content(
        private val films: ImmutableList<FilmState>,
        val genres: ImmutableList<String> = persistentListOf(),
        val selectedGenre: String? = null,
    ) : FilmsListState() {

        val filteredFilms: ImmutableList<FilmState>
            get() = films.filter { film ->
                if (selectedGenre == null) return@filter true
                film.genres.contains(selectedGenre.lowercase())
            }.sortedBy { film ->
                film.localizedName
            }.toImmutableList()
    }

    data object Loading : FilmsListState()

    data object Error : FilmsListState()
}

fun List<FilmEntity>.toScreenState(selectedGenre: String? = null) = FilmsListState.Content(
    films = this.map { it.toState() }.toImmutableList(),
    genres = this
        .flatMap { it.genres }
        .distinct()
        .map { genre ->
            genre.replaceFirstChar { firstChar ->
                firstChar.titlecase(Locale.getDefault())
            }
        }
        .toImmutableList(),
    selectedGenre = selectedGenre,
)

data class FilmState(
    val id: Int,
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double?,
    val imageUrl: String?,
    val description: String?,
    val genres: List<String>,
)

fun FilmEntity.toState() = FilmState(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres,
)

fun FilmState.toEntity() = FilmEntity(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres,
)