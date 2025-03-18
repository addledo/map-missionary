package com.example.mapmissionary.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.interfaces.LatLongProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val latLongProvider: LatLongProvider,
    private val gridRefProvider: GridRefProvider
) :
    ViewModel() {

    fun updateLatLong(sharedViewModel: SharedViewModel) {
        val gridRef = sharedViewModel.selectedLocation.gridRef ?: return
//        if (sharedViewModel.selectedLocation.coordinates != null) {
//            return
//        }

        viewModelScope.launch {
            val latLong = latLongProvider.getLatLongFromGridRef(gridRef)
            val updatedLocation = sharedViewModel.selectedLocation.copy(coordinates = latLong)
            sharedViewModel.updateSelectedLocation(updatedLocation)
        }
    }

    fun updateGridRef(sharedViewModel: SharedViewModel) {
        Log.d("ld_vm", "Inside updateGridRef")
        Log.d("ld_vm", "LatLong is ${sharedViewModel.selectedLocation.coordinates}")
        Log.d("ld_vm", "Grid ref is ${sharedViewModel.selectedLocation.gridRef}")
        val latLong = sharedViewModel.selectedLocation.coordinates ?: return
//        if (sharedViewModel.selectedLocation.gridRef != null) {
//            return
//        }

        Log.d("ld_vm", "About to launch scope")
        viewModelScope.launch {
            Log.d("ld_vm", "Inside scope")
            val gridRef = gridRefProvider.getGridFromLatLong(latLong)
            Log.d("ld_vm", "Grid ref is $gridRef")
            val updatedLocation = sharedViewModel.selectedLocation.copy(gridRef = gridRef)
            sharedViewModel.updateSelectedLocation(updatedLocation)
        }

    }
}