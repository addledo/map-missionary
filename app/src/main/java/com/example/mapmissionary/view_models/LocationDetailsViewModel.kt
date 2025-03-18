package com.example.mapmissionary.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapmissionary.interfaces.LatLongProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val latLongProvider: LatLongProvider,
) :
    ViewModel() {

    fun updateLatLong(sharedViewModel: SharedViewModel) {
        val gridRef = sharedViewModel.selectedLocation.gridRef ?: return
        if (sharedViewModel.selectedLocation.latLong != null) {
            return
        }

        viewModelScope.launch {
            val latLong = latLongProvider.getLatLongFromGridRef(gridRef)
            val updatedLocation = sharedViewModel.selectedLocation.copy(latLong = latLong)
            sharedViewModel.updateSelectedLocation(updatedLocation)
        }
    }
}