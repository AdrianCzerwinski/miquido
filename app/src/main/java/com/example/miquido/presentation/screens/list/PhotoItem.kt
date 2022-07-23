package com.example.miquido.presentation.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.miquido.data.remote.PicsumApi.Companion.BASE_URL
import com.example.miquido.ui.theme.LARGE_PADDING
import com.example.miquido.ui.theme.Teal200

@Composable
fun PhotoItem(
    id: String,
    onDetailClicked: () -> Unit
) {
    val url = "$BASE_URL/id/$id/1000/1000"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LARGE_PADDING)
            .height(400.dp)
            .clickable {
                onDetailClicked()
            },
        horizontalAlignment = Alignment.CenterHorizontally
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
                .wrapContentHeight()
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 20.dp,
                    color = Teal200
                )
        )
    }
    Text(text = "ID:  $id", fontSize = 24.sp, color = Teal200)
    Text(text = "CLICK 4 DETAILS", fontSize = 24.sp, color = Color.White)


}