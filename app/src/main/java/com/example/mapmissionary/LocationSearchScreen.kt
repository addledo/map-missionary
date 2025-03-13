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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
fun LocationSearchScreen(navController: NavController) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()

    // Is this clunky? Should it be injected directly?
    // Perhaps write a function in the shared view model so the gridRefService isn't needed?
    val gridRefService = sharedViewModel.gridRefService

    var userInput by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    //TODO Move this to a view model?
    var listOfLocations by remember { mutableStateOf(listOf<Location>()) }

    //TODO Do more research on scope and best way to implement this
    val coroutineScope = rememberCoroutineScope()

    val locationPermissionResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = {}
        )


    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Enter location name:") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
//                        listOfLocations = gridRefService.getListOfLocations(userInput)
                        //TODO Add logic
                    }
                })

        LazyColumn(
            modifier = Modifier.height(500.dp)
        ) {
            itemsIndexed(listOfLocations) { _, location ->
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    OutlinedCard(onClick = {
                        if (!location.gridRef.isNullOrBlank()) {
                            clipboardManager.setText(AnnotatedString(location.gridRef!!))
                            sharedViewModel.updateSelectedLocation(location)
                            navController.navigate("location_details")
                        }
                    }) {
                        Text(
                            text = location.toString(), modifier = Modifier.padding(10.dp)
                        )
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
            Button(
                onClick = {
                    locationPermissionResultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    navController.navigate("location_details")
                    //TODO Implement location
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(110.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Location icon",
                )
            }
            Button(
                onClick = {
                    // Alternative to run this when userInput is changed (wouldn't go here)
//                    LaunchedEffect(userInput) {
//                        listOfLocations = gridRefService.getListOfLocations(userInput)
//                    }
                    coroutineScope.launch {
                        listOfLocations = gridRefService.getListOfLocations(userInput)
                    }
                },
                modifier = Modifier
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
    }
}