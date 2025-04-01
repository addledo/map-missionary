package com.example.mapmissionary.view_models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.extensions.isGridRef
import com.example.mapmissionary.interfaces.LocationSearchProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val locationSearchProvider: LocationSearchProvider
) : ViewModel() {

    var errorMessage by mutableStateOf<String?>(null)

    var locations by mutableStateOf(listOf<Location>())
        private set

    private var searchJob: Job? = null
    private var lastSearchTerms = ""

    fun clearLocations() {
        locations = listOf()
    }


    fun runLocationSearch(searchTerms: String) {
        if (searchTerms.isBlank()) {
            return
        }

        val searchInProgress = searchJob?.isActive ?: false
        val newSearchTerms = searchTerms != lastSearchTerms
        if (searchInProgress && !newSearchTerms) {
            return
        }

        searchJob?.cancel()
        lastSearchTerms = searchTerms

        Log.d("GR", "Search terms == gridRef? ${searchTerms.isGridRef()}")
        if (searchTerms.isGridRef()) {
            val gridRef = searchTerms.uppercase().replace("\\w", "")
            locations = listOf(Location(gridRef = gridRef))
            return
        }

        searchJob = viewModelScope.launch {
            try {
                locations = locationSearchProvider.searchLocationsByKeywords(searchTerms)
            } catch (e: Exception) {
                errorMessage = e.toString()
            }
        }

    }
}