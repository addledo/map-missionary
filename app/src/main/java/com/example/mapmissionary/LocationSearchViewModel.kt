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
class LocationSearchViewModel @Inject constructor(private val locationHandler: LocationHandler) :
    ViewModel() {
    var locations by mutableStateOf(listOf<Location>())
        private set

    fun updateLocations(locations: List<Location>) {
        this.locations = locations
    }

    private suspend fun getCurrentLocation(): Location? {
        return locationHandler.getLocation()
        // TODO Make api request to get address and grid ref
    }

    fun hasLocationPermission(): Boolean {
        return locationHandler.hasPermission()
    }

    fun fetchAndUpdateLocation(sharedViewModel: SharedViewModel) {
        viewModelScope.launch {
            val currentLocation = getCurrentLocation()
            if (currentLocation != null) {
                sharedViewModel.updateSelectedLocation(currentLocation)
            }
        }
    }
}