package com.example.mapmissionary.view_models

import android.util.Log
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val deviceLocationHandler: DeviceLocationHandler,
    private val gridRefProvider: GridRefProvider,
    private val locationSearchProvider: LocationSearchProvider
) : ViewModel() {

    init {
        Log.d("vm", "Initialising search vm")
    }
    var locations by mutableStateOf(listOf<Location>())
        private set

    fun hasLocationPermission(): Boolean {
        return deviceLocationHandler.hasPermission()
    }

    fun fetchAndUpdateLocation(sharedViewModel: SharedViewModel) {
        viewModelScope.launch {
            val currentLocation = deviceLocationHandler.getLocation()?.copy(
                address = "Unspecified",
                gridRef = "loading..."
            )

            if (currentLocation == null) {
                sharedViewModel.updateSelectedLocation(Location.empty())
            } else {
                sharedViewModel.updateSelectedLocation(currentLocation)
                val gridRef = gridRefProvider.getGridFromLatLong(currentLocation.coordinates)
                val updatedLocation = currentLocation.copy(gridRef = gridRef)

                sharedViewModel.updateSelectedLocation(updatedLocation)
            }
        }
    }

    private var searchJob: Job? = null
    private var lastSearchTerms = ""

    fun runLocationSearch(searchTerms: String) {
        if (searchTerms.isBlank()) return

        val searchInProgress: Boolean = searchJob?.isActive ?: false
        val searchTermsHaveChanged = searchTerms != lastSearchTerms

        if (!searchInProgress || searchTermsHaveChanged) {
            searchJob?.cancel()
            lastSearchTerms = searchTerms
            searchJob = viewModelScope.launch {
                locations = locationSearchProvider.searchLocationsByKeywords(searchTerms)
            }
        }
    }
}