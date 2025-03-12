package com.example.mapmissionary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    var selectedLocation by mutableStateOf(Location())
        private set

    fun updateSelectedLocation(newLocation: Location) {
        selectedLocation = newLocation
    }

}