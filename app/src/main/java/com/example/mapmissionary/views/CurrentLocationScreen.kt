package com.example.mapmissionary.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mapmissionary.view_models.CurrentLocationViewModel
import com.example.mapmissionary.view_models.SharedViewModel

@Composable
fun CurrentLocationScreen(navController: NavController?) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val viewModel = hiltViewModel<CurrentLocationViewModel>()

    LaunchedEffect(key1 = sharedViewModel.selectedLocation.coordinates) {
        viewModel.updateGridRef(sharedViewModel)
    }

}