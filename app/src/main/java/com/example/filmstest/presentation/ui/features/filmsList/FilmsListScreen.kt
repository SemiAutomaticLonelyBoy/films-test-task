package com.example.filmstest.presentation.ui.features.filmsList

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.filmstest.R
import com.example.filmstest.navigation.Routes
import com.example.filmstest.presentation.ui.components.CustomSnackBar
import com.example.filmstest.presentation.ui.components.CustomSnackBarVisuals
import com.example.filmstest.presentation.ui.components.CustomTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilmsListScreen(
    navController: NavController,
) {
    val viewModel: FilmsListViewModel = koinViewModel()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.action.collect { action ->
            when (action) {
                is FilmsListViewModel.OutputAction.ShowError -> {
                    snackBarHostState.showSnackbar(
                        CustomSnackBarVisuals(
                            duration = SnackbarDuration.Indefinite,
                            withDismissAction = false,
                            onActionClick = action.onRefresh,
                            messageRes = action.message,
                            actionLabelRes = action.textButton,
                        )
                    )
                }

                is FilmsListViewModel.OutputAction.GoToFilmInfoScreen -> {
                    navController.navigate(route = Routes.FilmInfo(action.film))
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.films_list_top_bar_title),
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                val snackBarAction =
                    data.visuals as? CustomSnackBarVisuals ?: return@SnackbarHost

                CustomSnackBar(
                    message = stringResource(snackBarAction.messageRes),
                    actionText = stringResource(snackBarAction.actionLabelRes),
                    onActionClick = {
                        snackBarAction.onActionClick()
                        data.dismiss()
                    } ,
                )
            }
        }
    ) { paddingValues ->
        FilmListContent(
            paddingValues = paddingValues,
            sendAction = viewModel::inputAction,
            state = viewModel.state.collectAsState().value,
        )
    }
}
