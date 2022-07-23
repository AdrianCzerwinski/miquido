package com.example.miquido.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.miquido.data.remote.PicsumApi
import com.example.miquido.domain.model.Photo
import com.example.miquido.ui.theme.LARGE_PADDING
import com.example.miquido.ui.theme.Teal200

@Composable
fun DetailScreen(
    photo: Photo?
) {
    var showDetail by remember { mutableStateOf(false)}
    val url = "${PicsumApi.BASE_URL}/id/${photo?.id}/1200"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LARGE_PADDING)
            .background(color = Teal200)
            .clickable {
                showDetail = !showDetail
            }
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(url).build()
        )
        Image(
            painter = painter,
            contentDescription = "photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = Color.Green
                )
        )

        Spacer(modifier = Modifier.height(20.dp))




    }

}