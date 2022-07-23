package com.example.miquido.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.miquido.presentation.navigation.Screen
import com.example.miquido.ui.theme.SMALL_PADDING

@Composable
fun ListScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    var progressBarState = viewModel.progressBarState
    val photoList = viewModel.photoList
    var pageNumber = viewModel.pageNumber
    val message = viewModel.messageState

    LaunchedEffect(key1 = message) {

    }

    if(photoList.value.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = photoList.value,
                key = { photo ->
                    photo.id
                }
            ) { photo ->

                PhotoItem(
                    id = photo.id,
                    onDetailClicked = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "photo",
                            value = photo
                        )
                        navController.navigate(Screen.Detail.route)
                    }
                )
            }
        }
    }

    Button(onClick = { viewModel.getPhotos(1) }) {

    }





}