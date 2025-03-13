package com.example.mapmissionary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LocationSearchViewModel : ViewModel() {
    var locations by mutableStateOf(listOf<Location>())
        private set


    fun updateLocations(locations: List<Location>) {
        this.locations = locations
    }
}