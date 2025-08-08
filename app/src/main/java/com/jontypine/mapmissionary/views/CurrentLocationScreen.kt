package com.jontypine.mapmissionary.views

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jontypine.mapmissionary.shared_composables.BackButton
import com.jontypine.mapmissionary.shared_composables.GotoGoogleMapsButton
import com.jontypine.mapmissionary.shared_composables.InfoDialog
import com.jontypine.mapmissionary.shared_composables.LocationField
import com.jontypine.mapmissionary.shared_composables.PageTitle
import com.jontypine.mapmissionary.utilities.GoogleMapsLinkConstructor
import com.jontypine.mapmissionary.view_models.CurrentLocationViewModel

@Composable
fun CurrentLocationScreen() {
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
            .background(MaterialTheme.colorScheme.background)
            .padding(
                WindowInsets.systemBars.asPaddingValues(),
            )
            .padding(horizontal = 25.dp)
    ) {
        PageTitle("Current Location")
        LocationField("Grid Reference", viewModel.location.gridRef ?: "loading...")
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(top = 18.dp))
        LazyColumn(modifier = Modifier.weight(1F)) {
            item {
                LocationField("Coordinates", viewModel.location.latLong?.toString() ?: "loading...")
            }
            itemsIndexed(viewModel.location.extras) { _, field ->
                LocationField(field.first, field.second)
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 15.dp))
        GotoGoogleMapsButton(
            LocalContext.current,
            GoogleMapsLinkConstructor.getLinkFromLatLong(viewModel.location.latLong)
        )
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
        for (i in 1..4) {
            LocationField("Field", "Content")
        }
        Spacer(modifier = Modifier.weight(1F))
        BackButton(null)
    }
}