package com.example.mapmissionary.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mapmissionary.shared_composables.BackButton
import com.example.mapmissionary.shared_composables.LocationField
import com.example.mapmissionary.shared_composables.PageTitle
import com.example.mapmissionary.view_models.CurrentLocationViewModel
import com.example.mapmissionary.view_models.SharedViewModel

@Composable
fun CurrentLocationScreen(navController: NavController?) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val viewModel = hiltViewModel<CurrentLocationViewModel>()

    LaunchedEffect(key1 = sharedViewModel.selectedLocation.coordinates) {
        viewModel.updateGridRef(sharedViewModel)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {
        PageTitle("Current Location")
        LocationField("Grid Reference", sharedViewModel.selectedLocation.gridRef ?: "loading...")
        LocationField( "Coordinates", sharedViewModel.selectedLocation.coordinates?.toString() ?: "loading..." )
        Spacer(modifier = Modifier.weight(1F))
        BackButton(navController)
    }
}



@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {
        PageTitle("Current Location")

        LocationField("Grid Reference", "No location")
        LocationField("Coordinates", "No location")

        Spacer(modifier = Modifier.weight(1F))

        BackButton(null)
    }
}