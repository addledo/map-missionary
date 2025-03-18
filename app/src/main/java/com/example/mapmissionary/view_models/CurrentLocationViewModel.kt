package com.example.mapmissionary.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.utilities.DeviceLocationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentLocationViewModel @Inject constructor(
    private val gridRefProvider: GridRefProvider,
    private val deviceLocationHandler: DeviceLocationHandler
) : ViewModel() {
    var errorMessage by mutableStateOf<String?>(null)

    var location by mutableStateOf(Location())
        private set

    private fun clearLocation() {
        location = Location()
    }

    fun fetchAndUpdateLocation() {
        clearLocation()

        viewModelScope.launch {
            val currentLocation = deviceLocationHandler.getLocation() ?: return@launch
            location = currentLocation
        }.apply { invokeOnCompletion { updateGridRef() } }
    }

    fun hasLocationPermission(): Boolean {
        return deviceLocationHandler.hasPermission()
    }


    fun updateGridRef() {
        val latLong = location.latLong ?: return
        if (location.gridRef != null) {
            return
        }

        viewModelScope.launch {
            try {
                val gridRef = gridRefProvider.getGridFromLatLong(latLong)
                val updatedLocation = location.copy(gridRef = gridRef)
                location = updatedLocation
            } catch (e: Exception) {
                errorMessage = e.toString()
            }
        }
    }
}