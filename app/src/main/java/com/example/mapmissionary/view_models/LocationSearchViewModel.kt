package com.example.mapmissionary.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapmissionary.utilities.DeviceLocationHandler
import com.example.mapmissionary.utilities.GeoDojoService
import com.example.mapmissionary.data.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val deviceLocationHandler: DeviceLocationHandler,
    private val geoDojoService: GeoDojoService,
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
                // TODO Perhaps high abstraction converter class for this?
                val gridRef = currentLocation.coordinates?.getGridRef(geoDojoService)
                val updatedLocation = currentLocation.copy(gridRef = gridRef)

                sharedViewModel.updateSelectedLocation(updatedLocation)
            }
        }
    }

    fun runLocationSearch(searchTerms: String) {
        viewModelScope.launch {
            locations = geoDojoService.searchLocationsByKeywords(searchTerms)
        }
    }
}