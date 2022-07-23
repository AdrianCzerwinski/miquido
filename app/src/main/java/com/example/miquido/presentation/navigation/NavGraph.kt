package com.example.miquido.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.miquido.domain.model.Photo
import com.example.miquido.presentation.screens.detail.DetailScreen
import com.example.miquido.presentation.screens.list.ListScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.List.route){
        composable(route = Screen.List.route){
            ListScreen(navController = navHostController)
        }
        composable(route = Screen.Detail.route){
            val photo =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<Photo>("photo")
            DetailScreen(photo = photo)
        }
    }

}