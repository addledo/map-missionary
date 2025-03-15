package com.example.mapmissionary.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mapmissionary.data.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() :
    ViewModel() {
    var selectedLocation by mutableStateOf(Location())
        private set

    fun updateSelectedLocation(newLocation: Location) {
        selectedLocation = newLocation
    }
}