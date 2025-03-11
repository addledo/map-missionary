package com.example.mapmissionary

import GridRefService
import Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomePage(gridRefService: GridRefService) {
    var userInput by remember { mutableStateOf("") }
    var listOfLocations by remember { mutableStateOf(listOf(Location(), Location())) }


    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Enter location name:") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 80.dp)
        )

        for (location in listOfLocations) {
            Text(
                text = location.address + location.gridRef,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
        }

        Button(
            onClick = {listOfLocations = gridRefService.getListOfLocations(userInput)},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(100.dp),
        ) {
            Text(
                "Find",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(7.dp)
            )
        }
    }
}