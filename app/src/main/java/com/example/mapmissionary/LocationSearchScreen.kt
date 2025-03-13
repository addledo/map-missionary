package com.example.mapmissionary

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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
fun LocationSearchScreen(navController: NavController?) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val viewModel = hiltViewModel<LocationSearchViewModel>()

    var userInput by remember { mutableStateOf("plas y brenin") }

    //TODO Do more research on scope and best way to implement this
    val coroutineScope = rememberCoroutineScope()

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {})


    Column(modifier = Modifier.fillMaxSize()) {
        UserInputBox(userInput) { userInput = it }

        LazyColumn(
            modifier = Modifier.height(500.dp)
        ) {
            itemsIndexed(viewModel.locations) { _, location ->
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    LocationCard(location) {
                        navController?.navigate("location_details")
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            CurrentLocationButton {
                locationPermissionResultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                navController?.navigate("location_details")
                //TODO Implement location
            }
            FindButton {
                coroutineScope.launch {
                    viewModel.updateLocations(sharedViewModel.searchLocations(userInput))
                }
            }
        }
    }


}







@Composable
fun LocationCard(location: Location, onClick: () -> Unit) {
    OutlinedCard(onClick = onClick) {
        Text(
            text = location.toString(), modifier = Modifier.padding(10.dp)
        )
    }

}

@Composable
fun LocationResultsDisplay() {
    TODO("Not yet implemented")
}

@Composable
fun UserInputBox(userInput: String, onValueChange: (String) -> Unit) {
    TextField(
        value = userInput,
        onValueChange = onValueChange,
        label = { Text("Enter location name:") },
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
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
    Column {
        CurrentLocationButton()
        FindButton()

    }
}