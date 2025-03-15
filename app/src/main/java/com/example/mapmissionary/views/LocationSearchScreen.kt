package com.example.mapmissionary.views

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.view_models.LocationSearchViewModel
import com.example.mapmissionary.view_models.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun LocationSearchScreen(navController: NavController?) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val viewModel = hiltViewModel<LocationSearchViewModel>()

    var userInput by remember { mutableStateOf("Plas y Brenin") }

    val onLocationSelected: (Location) -> Unit = {
        location ->
        sharedViewModel.updateSelectedLocation(location)
        navController?.navigate("location_details")
    }

    //TODO Do more research on scope and best way to implement this
    val coroutineScope = rememberCoroutineScope()

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {})

    Column(modifier = Modifier.fillMaxSize()) {

        UserInputBox(userInput) { userInput = it }

        LocationResultsLazyColumn(
            viewModel.locations,
            Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            onLocationSelected = onLocationSelected
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .padding(top = 30.dp)
        ) {
            CurrentLocationButton {
                locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                if (viewModel.hasLocationPermission()) {
                    sharedViewModel.updateSelectedLocation(
                        Location(
                            address = "loading...",
                            gridRef = "loading...",
                            coordinates = "loading...",
                        )
                    )
                    navController?.navigate("location_details")
                    viewModel.fetchAndUpdateLocation(sharedViewModel)
                }
            }
            FindButton {
                coroutineScope.launch {
                    viewModel.runLocationSearch(userInput)
                }
            }
        }
    }
}


@Composable
fun LocationCard(location: Location, onLocationSelected: (Location) -> Unit) {
    OutlinedCard(
        onClick = { onLocationSelected(location) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)

    ) {
        Text(
            text = formatLocationCardText(location),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(15.dp)
        )
    }
}

fun formatLocationCardText(location: Location): String {
    val newLine = System.lineSeparator()
    val builder = StringBuilder()
    builder.append(location.address ?: "Address not found")
    builder.append(newLine)
    builder.append(location.gridRef ?: "Grid reference not found")
    return builder.toString()
}

@Composable
fun LocationResultsLazyColumn(
    locations: List<Location>, modifier: Modifier, onLocationSelected: (Location) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(locations) { _, location ->
            Row(
                modifier = Modifier
            ) {
                LocationCard(location, onLocationSelected)
            }
        }
    }
}

@Composable
fun UserInputBox(userInput: String, onValueChange: (String) -> Unit) {
    TextField(
        value = userInput,
        textStyle = TextStyle(fontSize = 20.sp),
        onValueChange = onValueChange,
        label = { Text("Enter location name:") },
        modifier = Modifier
            .padding(25.dp)
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {

            }
        )
    )
}


@Composable
fun CurrentLocationButton(onClick: () -> Unit = {}) {
    Button(
        onClick = onClick, modifier = Modifier
            .height(50.dp)
            .width(110.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Location icon",
        )
    }
}

@Composable
fun FindButton(onClick: () -> Unit = {}) {
    Button(
        onClick = onClick, modifier = Modifier
            .height(50.dp)
            .width(110.dp)
    ) {
        Text(
            "Find",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationSearchScreenPrev() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UserInputBox("Plas y Brenin") {}
        LocationResultsLazyColumn(
            listOf(Location(), Location()),
            Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {}

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .padding(top = 10.dp)
        ) {
            CurrentLocationButton { }
            FindButton { }
        }
    }
}