package com.example.filmstest.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.filmstest.domain.etities.FilmEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val FilmType = object : NavType<FilmEntity>(
    isNullableAllowed = false,
) {
    override fun put(bundle: Bundle, key: String, value: FilmEntity) {
        bundle.putParcelable(key, value)
    }
    override fun get(bundle: Bundle, key: String): FilmEntity? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, FilmEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun serializeAsValue(value: FilmEntity): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun parseValue(value: String): FilmEntity {
        return Json.decodeFromString<FilmEntity>(value)
    }
}