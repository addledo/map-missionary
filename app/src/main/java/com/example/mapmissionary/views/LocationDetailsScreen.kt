package com.example.mapmissionary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mapmissionary.shared_composables.BackButton
import com.example.mapmissionary.shared_composables.GotoGoogleMapsButton
import com.example.mapmissionary.shared_composables.InfoDialog
import com.example.mapmissionary.shared_composables.LocationField
import com.example.mapmissionary.shared_composables.PageTitle
import com.example.mapmissionary.utilities.GoogleMapsLinkConstructor
import com.example.mapmissionary.view_models.LocationDetailsViewModel
import com.example.mapmissionary.view_models.SharedViewModel

@Composable
fun LocationDetailsScreen() {
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val viewModel = hiltViewModel<LocationDetailsViewModel>()

    if (viewModel.errorMessage != null) {
        InfoDialog(
            title = "Information",
            message = viewModel.errorMessage ?: "A problem occurred",
            onDismiss = { viewModel.errorMessage = null }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.updateLatLong(sharedViewModel)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {
        PageTitle("Location")
        if (sharedViewModel.selectedLocation.address != null) {
            LocationField("Address", sharedViewModel.selectedLocation.address ?: "Unavailable")
        }
        LocationField("Grid Reference", sharedViewModel.selectedLocation.gridRef ?: "Unavailable")
        LocationField("Coordinates", sharedViewModel.selectedLocation.latLong?.toString() ?: "loading...")
        Spacer(modifier = Modifier.weight(1F))
        if (sharedViewModel.selectedLocation.latLong != null) {
            GotoGoogleMapsButton(
                LocalContext.current,
                GoogleMapsLinkConstructor.getLinkFromLatLong(sharedViewModel.selectedLocation.latLong)
            )
        }
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
        PageTitle("Location")

        LocationField("Address", "No location")
        LocationField("Grid Reference", "No location")
        LocationField("Coordinates", "No location")

        Spacer(modifier = Modifier.weight(1F))

        BackButton(null)
    }
}