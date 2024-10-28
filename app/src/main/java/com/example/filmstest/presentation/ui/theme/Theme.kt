package com.example.filmstest.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    secondary = Yellow,
    onBackground = Black,
    surface = LightGrey,
    onSurface = OnLightGrey,
    surfaceVariant = Grey,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = Yellow,
    onBackground = Black,
    surface = LightGrey,
    onSurface = OnLightGrey,
    surfaceVariant = Grey,
)

@Composable
fun FilmsTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
