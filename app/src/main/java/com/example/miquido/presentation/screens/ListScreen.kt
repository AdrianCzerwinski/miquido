package com.example.miquido.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.miquido.domain.model.Photo
import com.example.miquido.ui.theme.SMALL_PADDING

@Composable
fun ListScreen(
    navController: NavHostController
) {
    val examplePhoto = Photo(
        author = "author",
        id = "201",
        url = "https://unsplash.com/photos/yC-Yzbqy7PY",
        download_url = "https://picsum.photos/id/0/5616/3744",
        width = 5616,
        height = 3744
    )
    val examplePhoto2 = Photo(
        author = "authorrr",
        id = "200",
        url = "https://unsplash.com/photos/yC-Yzbqy7PY",
        download_url = "https://picsum.photos/id/0/5616/3744",
        width = 5616,
        height = 3744
    )

    val itemList = listOf(examplePhoto, examplePhoto2)

    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(
            items = itemList,
            key = { photo ->
                photo.id
            }
        ) { photo ->

            PhotoItem(url = photo.download_url, id = photo.id) {

            }
        }
    }


}