package com.example.filmstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.example.filmstest.domain.etities.FilmEntity
import com.example.filmstest.navigation.FilmType
import com.example.filmstest.navigation.Routes
import com.example.filmstest.presentation.ui.features.filmInfo.FilmInfoScreen
import com.example.filmstest.presentation.ui.features.filmsList.FilmsListScreen
import com.example.filmstest.presentation.ui.theme.FilmsTestTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmsTestTheme {
                SetupImageLoader(enableDebugLogs = true)
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.FilmsList,
                ) {
                    composable<Routes.FilmsList> { FilmsListScreen(
                        navController = navController,
                    ) }
                    composable<Routes.FilmInfo>(
                        typeMap = mapOf(typeOf<FilmEntity>() to FilmType)
                    ) { backStackEntry ->
                        val film = backStackEntry.toRoute<Routes.FilmInfo>()
                        FilmInfoScreen(
                            film = film.film,
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SetupImageLoader(enableDebugLogs: Boolean) {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(context, enableDebugLogs)
    }
}

fun newImageLoader(
    context: PlatformContext,
    debug: Boolean,
): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(
                    context = context,
                    percent = 0.25,
                )
                .build()
        }
        .crossfade(true)
        .apply {
            if (debug) {
                logger(DebugLogger())
            }
        }
        .build()
}
