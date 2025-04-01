package com.example.mapmissionary.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapmissionary.views.CurrentLocationScreen
import com.example.mapmissionary.views.LocationDetailsScreen
import com.example.mapmissionary.views.LocationSearchScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable(route = Screen.Search.route) { LocationSearchScreen(navController) }
        composable(route = Screen.LocationDetails.route) { LocationDetailsScreen() }
        composable(route = Screen.CurrentLocation.route) { CurrentLocationScreen() }
    }
}