package com.jontypine.mapmissionary.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jontypine.mapmissionary.interfaces.LatLongProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val latLongProvider: LatLongProvider,
) :
    ViewModel() {
    var errorMessage by mutableStateOf<String?>(null)

    fun updateLatLong(sharedViewModel: SharedViewModel) {
        val gridRef = sharedViewModel.selectedLocation.gridRef ?: return
        if (sharedViewModel.selectedLocation.latLong != null) {
            return
        }

        viewModelScope.launch {
            try {
                val latLong = latLongProvider.getLatLongFromGridRef(gridRef)
                val updatedLocation = sharedViewModel.selectedLocation.copy(latLong = latLong)
                sharedViewModel.updateSelectedLocation(updatedLocation)
            } catch (e: Exception) {
                errorMessage = e.toString()
            }
        }
    }
}