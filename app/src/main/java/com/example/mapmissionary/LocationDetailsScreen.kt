package com.example.mapmissionary

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun LocationDetailsScreen(navController: NavController) {
    Text(
        text = "Location details screen"
    )
    Button(
        onClick = { navController.popBackStack()}
    ) {
        Text(text = "Go back")
    }
}