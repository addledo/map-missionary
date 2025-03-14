package com.example.mapmissionary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val locationHandler: LocationHandler, private val gridRefService: GridRefService
) : ViewModel() {
    var locations by mutableStateOf(listOf<Location>())
        private set

    fun updateLocations(locations: List<Location>) {
        this.locations = locations
    }

    private suspend fun getCurrentLocation(): Location? {
        val location = locationHandler.getLocation()
        return location
    }

    fun hasLocationPermission(): Boolean {
        return locationHandler.hasPermission()
    }


    fun fetchAndUpdateLocation(sharedViewModel: SharedViewModel) {
        viewModelScope.launch {
            val currentLocation = getCurrentLocation()?.copy(gridRef = "loading...")

            if (currentLocation != null) {
                sharedViewModel.updateSelectedLocation(currentLocation)
                val gridRef = gridRefService.getGridFromCoordinates(currentLocation.coordinates)
                val updatedLocation = currentLocation.copy(gridRef = gridRef)

                sharedViewModel.updateSelectedLocation(updatedLocation)
            }
        }
    }
}