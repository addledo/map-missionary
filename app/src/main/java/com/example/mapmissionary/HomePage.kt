package com.example.mapmissionary

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val viewModel = MainViewModel()

@Composable
fun HomePage(gridRefService: GridRefService) {
    var userInput by remember { mutableStateOf("") }
    var listOfLocations by remember { mutableStateOf(listOf<Location>()) }
    val clipboardManager = LocalClipboardManager.current
    val viewModel = MainViewModel()
    val dialogueQueue = viewModel.visiblePermissionDialogueQueue
    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = android.Manifest.permission.ACCESS_FINE_LOCATION, isGranted = isGranted
            )
        })


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
                        listOfLocations = gridRefService.getListOfLocations(userInput)
                    }
                })

        LazyColumn(
            modifier = Modifier.height(500.dp)
        ) {
            itemsIndexed(listOfLocations) { i, location ->
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    OutlinedCard(onClick = {
                        if (!location.gridRef.isNullOrBlank()) {
                            clipboardManager.setText(AnnotatedString(location.gridRef!!))
                        }
                    }) {
                        Text(
                            text = location.address.toString(), modifier = Modifier.padding(10.dp)
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
                onClick = { locationPermissionResultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) },
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
                onClick = { listOfLocations = gridRefService.getListOfLocations(userInput) },
                modifier = Modifier
                    .height(50.dp)
                    .width(110.dp)
            ) {
                Text(
                    "Find",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.padding(7.dp)
                )
            }
        }
    }
}