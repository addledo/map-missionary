package com.example.mapmissionary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val gridRefService: GridRefService) :
    ViewModel() {
    var selectedLocation by mutableStateOf(Location())
        private set

    fun updateSelectedLocation(newLocation: Location) {
        selectedLocation = newLocation
    }

    suspend fun searchLocations(keywords: String): List<Location> {
        return gridRefService.getListOfLocations(keywords)
    }
}