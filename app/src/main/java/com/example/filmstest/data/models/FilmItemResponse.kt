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
    @SerialName("id") val id: Int,
    @SerialName("localized_name") val localizedName: String,
    @SerialName("name") val name: String,
    @SerialName("year") val year: Int,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("genres") val genres: List<String>,
)

fun FilmItem.toEntity() = FilmEntity(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres,
)