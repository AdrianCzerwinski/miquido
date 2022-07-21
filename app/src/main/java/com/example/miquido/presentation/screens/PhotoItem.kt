package com.example.miquido.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.miquido.domain.model.Photo
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.miquido.ui.theme.MEDIUM_PADDING

@Composable
fun PhotoItem(
    url: String,
    id: String,
    onDetailClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING)
            .height(400.dp)
            .clickable {
                onDetailClicked()
            }
    ){
        Row(
            modifier = Modifier.height(200.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(url).build()
            )
            Image(
                painter = painter,
                contentDescription = "photo"
            )
        }
        Row(modifier = Modifier
            .height(80.dp)
            .padding(vertical = 8.dp)) {
            Text(text = id, fontSize = 14.sp)
        }
        
    }
}