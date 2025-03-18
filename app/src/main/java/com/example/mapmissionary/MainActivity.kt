package com.example.mapmissionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapmissionary.ui.theme.MapMissionaryTheme
import com.example.mapmissionary.views.CurrentLocationScreen
import com.example.mapmissionary.views.LocationDetailsScreen
import com.example.mapmissionary.views.LocationSearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MapMissionaryTheme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") { LocationSearchScreen(navController) }
        composable("location_details") { LocationDetailsScreen(navController) }
        composable("current_location") { CurrentLocationScreen(navController) }
    }
}