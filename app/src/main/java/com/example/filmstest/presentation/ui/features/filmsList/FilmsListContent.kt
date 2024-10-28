package com.example.filmstest.presentation.ui.features.filmsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.TypedArrayUtils.getString
import com.example.filmstest.R
import com.example.filmstest.presentation.ui.components.FilmCard
import com.example.filmstest.utils.conditional


@Composable
fun FilmListContent(
    paddingValues: PaddingValues,
    state: FilmsListScreenState,
    sendAction: (FilmsListViewModel.InputAction) -> Unit,
) {

    when (state.screenState) {
        FilmsListState.Error -> Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = paddingValues.calculateTopPadding()),
        )

        is FilmsListState.Content -> {
            Success(
                paddingValues = paddingValues,
                state = state.screenState,
                sendAction = sendAction,
            )
        }

        FilmsListState.Loading -> Loading()
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    )
}

@Composable
private fun Success(
    state: FilmsListState.Content,
    paddingValues: PaddingValues,
    sendAction: (FilmsListViewModel.InputAction) -> Unit,
) {

    val cols = 2
    LazyVerticalGrid(
        modifier = Modifier.padding(
            top = paddingValues.calculateTopPadding(),
        ),
        columns = GridCells.Fixed(cols),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = paddingValues.calculateBottomPadding(),
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        if (state.genres.isNotEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 16.dp,
                    ),
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                    text = stringResource(R.string.films_list_genres),
                )
            }
            state.genres.forEachIndexed() { index, item ->
                item(span = { GridItemSpan(cols) }) {
                    key(index) {
                        val color = MaterialTheme.colorScheme.secondary
                        Text(
                            modifier = Modifier
                                .clickable(onClick = {
                                    sendAction(FilmsListViewModel.InputAction.SetGenre(item))
                                })
                                .conditional(item == state.selectedGenre) {
                                    Modifier.background(color)
                                }
                                .padding(
                                    vertical = 10.dp,
                                    horizontal = 16.dp,
                                ),
                            text = item,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }
        item(span = { GridItemSpan(2) }) {
            Text(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 16.dp,
                ),
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                text = stringResource(R.string.films_list_films),
            )
        }
        itemsIndexed(state.filteredFilms) { index, film ->
            FilmCard(
                modifier = Modifier
                    .padding(
                        start = if (index % 2 == 0) 16.dp else 0.dp,
                        end = if (index % 2 == 0) 0.dp else 16.dp,
                        bottom = 16.dp,
                    ),
                film = film.toEntity(),
                onClick = {
                    sendAction(FilmsListViewModel.InputAction.OnFilmCardClick(film))
                },
            )
        }
    }
}
