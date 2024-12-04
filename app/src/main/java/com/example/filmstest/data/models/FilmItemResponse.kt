package com.example.filmstest.data.models

import com.example.filmstest.domain.etities.FilmEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponse(
    @SerialName("films") val films: List<FilmItem>,
)

@Serializable
data class FilmItem (
    @SerialName("id") val id: Int? = null,
    @SerialName("localized_name") val localizedName: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("genres") val genres: List<String>? = null,
)

fun FilmItem.toEntity() = FilmEntity(
    id = requireNotNull(id),
    localizedName = requireNotNull(localizedName),
    name = requireNotNull(name),
    year = requireNotNull(year),
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = requireNotNull(genres),
)