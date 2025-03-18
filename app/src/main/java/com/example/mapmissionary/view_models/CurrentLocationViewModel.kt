package com.example.mapmissionary.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapmissionary.interfaces.GridRefProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentLocationViewModel @Inject constructor(
    private val gridRefProvider: GridRefProvider
): ViewModel() {

    fun updateGridRef(sharedViewModel: SharedViewModel) {
        val latLong = sharedViewModel.selectedLocation.coordinates ?: return
        if (sharedViewModel.selectedLocation.gridRef != null) {
            return
        }

        viewModelScope.launch {
            val gridRef = gridRefProvider.getGridFromLatLong(latLong)
            val updatedLocation = sharedViewModel.selectedLocation.copy(gridRef = gridRef)
            sharedViewModel.updateSelectedLocation(updatedLocation)
        }
    }
}