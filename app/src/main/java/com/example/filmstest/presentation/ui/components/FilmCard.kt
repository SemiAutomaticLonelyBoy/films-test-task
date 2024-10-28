package com.example.filmstest.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.filmstest.domain.etities.FilmEntity

@Composable
fun FilmCard(
    film: FilmEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
    ) {
        CustomImage(
            imageUrl = film.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(278.dp)
                .clip(RoundedCornerShape(4.dp)),
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = film.localizedName,
            minLines = 2,
            maxLines = 2,
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
