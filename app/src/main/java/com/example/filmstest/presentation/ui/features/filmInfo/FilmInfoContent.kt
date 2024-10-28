package com.example.filmstest.presentation.ui.features.filmInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.filmstest.R
import com.example.filmstest.presentation.ui.components.CustomImage
import java.util.Locale

@Composable
fun FilmInfoContent(
    paddingValues: PaddingValues,
    state: FilmInfoScreenState,
) {
    if (state.film == null) return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = paddingValues.calculateTopPadding() + 24.dp,
                bottom = paddingValues.calculateBottomPadding(),
            ),

    ) {
        CustomImage(
            imageUrl = state.film.imageUrl,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
                .widthIn(min = 132.dp)
                .heightIn(min = 200.dp)
                .clip(RoundedCornerShape(4.dp)),
        )
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            text = state.film.localizedName,
            style = MaterialTheme.typography.displayMedium,
            minLines = 1,
            overflow = TextOverflow.Clip,
        )
        val genres =
            if (state.film.genres.isEmpty()) "" else "${state.film.genres.joinToString(", ")}, "
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "$genres${state.film.year} ${stringResource(R.string.film_info_year)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.surfaceVariant,
            minLines = 1,
            overflow = TextOverflow.Clip,
        )
        if (state.film.rating != null) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp,
                    ),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .alignByBaseline(),
                    text = String
                        .format(Locale.getDefault(), "%.1f", state.film.rating)
                        .replace(',','.'),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = stringResource(R.string.film_info_kinopoisk),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Text(
            modifier = Modifier.padding(
                top = 4.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            ),
            text = state.film.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}