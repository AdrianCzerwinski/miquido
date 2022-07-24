package com.example.miquido.presentation.screens.detail

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.miquido.R
import com.example.miquido.data.remote.PicsumApi
import com.example.miquido.domain.model.Photo
import com.example.miquido.presentation.screens.MainViewModel
import com.example.miquido.ui.theme.*

@Composable
fun DetailScreen(
    photo: Photo?,
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var showDetail by remember { mutableStateOf(false) }
    val url = "${PicsumApi.BASE_URL}/id/${photo?.id}/1200"
    val error = viewModel.detailErrorState

    LaunchedEffect(key1 = error) {
        if (error.message.isNotBlank())
            Toast.makeText(
                context,
                error.message,
                Toast.LENGTH_SHORT
            ).show()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Purple500),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple500)
                    .clickable {
                        navController.navigateUp()
                    }
                    .padding(LARGE_PADDING),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(
                        R.string.back
                    ),
                    tint = Color.White,
                )
                Text(text = stringResource(id = R.string.back), color = Teal200)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Purple500)
            ) {

                val painter = rememberAsyncImagePainter(
                    model = url,
                    onError = { error ->
                        viewModel.detailErrorToDetailsScreen(e = error.result.throwable.message)
                    }
                )
                Image(
                    painter = painter,
                    contentDescription = "photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            width = 20.dp,
                            color = Teal200
                        )
                        .padding(SMALL_PADDING)
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        },
        bottomBar = {
            Card(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    )
                    .background(Teal200)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Teal200)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDetail = !showDetail
                            }
                    ) {
                        val icon =
                            if (showDetail) Icons.Default.ArrowDownward
                            else Icons.Default.ArrowUpward
                        Icon(
                            modifier = Modifier
                                .padding(LARGE_PADDING)
                                .size(40.dp),
                            imageVector = icon,
                            contentDescription = stringResource(
                                R.string.arrow
                            ),
                            tint = Purple500
                        )
                    }
                    if (showDetail) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = LARGE_PADDING,
                                    end = LARGE_PADDING,
                                    bottom = SUPERLARGE_PADDING
                                ),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Photography ID: ${photo?.id}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Author: ${photo?.author}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Width: ${photo?.width}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Height: ${photo?.height}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Download URL: ${photo?.download_url}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}