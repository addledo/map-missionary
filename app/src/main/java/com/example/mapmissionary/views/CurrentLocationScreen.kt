package com.example.mapmissionary.views

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.mapmissionary.shared_composables.InfoDialog
import com.example.mapmissionary.shared_composables.LocationField
import com.example.mapmissionary.shared_composables.PageTitle
import com.example.mapmissionary.view_models.CurrentLocationViewModel

@Composable
fun CurrentLocationScreen(navController: NavController?) {
    val viewModel = hiltViewModel<CurrentLocationViewModel>()

    if (viewModel.errorMessage != null) {
        InfoDialog(
            title = "Information",
            message = viewModel.errorMessage ?: "A problem occurred",
            onDismiss = { viewModel.errorMessage = null }
        )
    }

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                viewModel.fetchAndUpdateLocation()
            }
        })

    LaunchedEffect(Unit) {
        if (!viewModel.hasLocationPermission()) {
            locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.fetchAndUpdateLocation()
        }
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
        LocationField("Grid Reference", viewModel.location.gridRef ?: "loading...")
        LocationField(
            "Coordinates",
            viewModel.location.latLong?.toString() ?: "loading..."
        )
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