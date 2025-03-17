package com.example.mapmissionary.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.interfaces.LocationSearchProvider
import com.example.mapmissionary.utilities.DeviceLocationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val deviceLocationHandler: DeviceLocationHandler,
    private val gridRefProvider: GridRefProvider,
    private val locationSearchProvider: LocationSearchProvider
) : ViewModel() {

    var locations by mutableStateOf(listOf<Location>())
        private set

    private suspend fun getCurrentLocation(): Location? {
        val location = deviceLocationHandler.getLocation()
        return location
    }

    fun hasLocationPermission(): Boolean {
        return deviceLocationHandler.hasPermission()
    }


    fun fetchAndUpdateLocation(sharedViewModel: SharedViewModel) {
        viewModelScope.launch {
            val currentLocation = getCurrentLocation()?.copy(gridRef = "loading...")

            if (currentLocation != null) {
                sharedViewModel.updateSelectedLocation(currentLocation)
                val gridRef = gridRefProvider.getGridFromLatLong(currentLocation.coordinates)
                val updatedLocation = currentLocation.copy(gridRef = gridRef)

                sharedViewModel.updateSelectedLocation(updatedLocation)
            }
            // TODO Add logic for if null
        }
    }

    fun runLocationSearch(searchTerms: String) {
        viewModelScope.launch {
            locations = locationSearchProvider.searchLocationsByKeywords(searchTerms)
        }
    }
}