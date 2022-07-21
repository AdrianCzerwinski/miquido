package com.example.miquido.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.List.route){
        composable(route = Screen.List.route){

        }
        composable(route = Screen.Detail.route){

        }
    }

}