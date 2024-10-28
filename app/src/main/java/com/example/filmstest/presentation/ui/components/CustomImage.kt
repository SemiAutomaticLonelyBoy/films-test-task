package com.example.filmstest.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

@Composable
fun CustomImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {

    var isProcessingError by remember { mutableStateOf(false) }
    val image: AsyncImagePainter = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { asyncImageState ->
            if (asyncImageState is AsyncImagePainter.State.Error) {
                isProcessingError = true
            } else if (asyncImageState is AsyncImagePainter.State.Success) {
                isProcessingError = false
            }
        },
    )

    when (imageUrl) {
        null -> EmptyImage(modifier = modifier)

        else -> {
            if (isProcessingError) {
                EmptyImage(modifier = modifier)
            } else {
                Image(
                    modifier = modifier.background(MaterialTheme.colorScheme.surface),
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}
