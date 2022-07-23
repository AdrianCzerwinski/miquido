package com.example.miquido.presentation.screens.list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.miquido.R
import com.example.miquido.presentation.navigation.Screen
import com.example.miquido.presentation.screens.MainViewModel
import com.example.miquido.ui.theme.LARGE_PADDING
import com.example.miquido.ui.theme.Purple500
import com.example.miquido.ui.theme.Teal200

@Composable
fun ListScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(key1 = state.error) {
        if (state.error.message.isNotBlank())
        Toast.makeText(
            context,
            state.error.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Purple500)
    ) {
        if (state.progressBarState) {
            CircularProgressIndicator(modifier = Modifier.size(80.dp), color = Teal200)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(state.photos.size) { i ->
                    val photo = state.photos[i]
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Purple500)
                    ) {
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
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Teal200)
                            .padding(LARGE_PADDING),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = Teal200)
                        } else {
                            Button(
                                onClick = {
                                    viewModel.pageUp()
                                },
                                modifier = Modifier.background(Teal200)
                            ) {
                                Text(
                                    text = stringResource(R.string.load_more),
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}