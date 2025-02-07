package com.example.mbapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Route {
    Main,
    Detail
}

@Composable
fun MBNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Main.name) {
        composable(route = Route.Main.name) {
            MainScreen(modifier = modifier) {
                navController.navigate(Route.Detail.name)
            }
        }
        composable(route = Route.Detail.name) {
            val mainViewModel = hiltViewModel<MainViewModel>(navController.getBackStackEntry(Route.Main.name))
            mainViewModel.currentThread?.run {
                DetailScreen(
                    modifier = modifier,
                    headMessage = this,
                ) {
                    navController.popBackStack()
                }
            }
        }
    }
}