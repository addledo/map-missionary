package com.example.mapmissionary.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mapmissionary.interfaces.LatLongProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(private val latLongProvider: LatLongProvider) :
    ViewModel() {
        init {
            Log.d("vm", "Initialising details vm")
        }

    suspend fun updateLatLong(sharedViewModel: SharedViewModel) {
        val gridRef = sharedViewModel.selectedLocation.gridRef ?: return

        val latLong = latLongProvider.getLatLongFromGridRef(gridRef)
        val updatedLocation = sharedViewModel.selectedLocation.copy(coordinates = latLong)
        sharedViewModel.updateSelectedLocation(updatedLocation)
    }
}