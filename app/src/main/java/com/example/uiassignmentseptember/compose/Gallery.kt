package com.example.uiassignmentseptember.compose

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.uiassignmentseptember.getImageIds

@Composable
fun Gallery(
    context: Context
) {
    val images = getImageIds(context)
    Surface(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(
                images,
                key = { it }
            ) {
                PhotoInGallery(imageId = it)
            }
        }
    }
}

@Composable
fun PhotoInGallery(
    imageId:Int
) {
    val size = LocalConfiguration.current.screenWidthDp / 2
    Card(
        modifier = Modifier
            .size(size.dp)
            .padding(5.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageId).apply(block = fun ImageRequest.Builder.() {
                crossfade(500)
            }).build()
        )

        val painterState = painter.state

        if (painterState == AsyncImagePainter.State.Loading(painter)) {
            CircularProgressIndicator()
        } else {
            Image(
                painter = painter,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
    }
}