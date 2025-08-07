package com.example.mapmissionary.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.data.SearchResult
import com.example.mapmissionary.extensions.isGridRef
import com.example.mapmissionary.extensions.isLatLong
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.interfaces.LocationSearchProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val locationSearchProvider: LocationSearchProvider,
    private val gridRefProvider: GridRefProvider
) : ViewModel() {

    var errorMessage by mutableStateOf<String?>(null)

    var locations by mutableStateOf(listOf<Location>())
        private set

    private var searchJob: Job? = null
    private var lastSearchTerms = ""

    private suspend fun runLatLongConversion(searchTerms: String): SearchResult {
        val lat: Double
        val long: Double

        try {
            lat = searchTerms.substringBefore(',').toDouble()
            long = searchTerms.substringAfter(',').toDouble()
        } catch (_: NumberFormatException) {
            return SearchResult.FAIL("Incorrect number format entered")
        }

        val latLong = LatLong(lat, long)
        val gridRef = gridRefProvider.getGridFromLatLong(latLong)
        locations = listOf(Location(latLong = latLong, gridRef = gridRef))
        return SearchResult.CONVERSION
    }

    private fun setLocationToGridRef(searchTerms: String) {
        // Replace consecutive spaces with a single space
        val gridRef = searchTerms.uppercase().replace(Regex("\\s{2,}"), " ")
        // Conversion for this takes place in Location Details Screen
        locations = listOf(Location(gridRef = gridRef))
    }

    suspend fun runLocationSearch(searchTerms: String): SearchResult {
        if (searchTerms.isBlank()) {
            return SearchResult.FAIL("No search terms")
        }

        if (searchTerms.isLatLong()) {
            return runLatLongConversion(searchTerms)
        }

        if (searchTerms.isGridRef()) {
            setLocationToGridRef(searchTerms)
            return SearchResult.CONVERSION
        }

        val searchInProgress = searchJob?.isActive ?: false
        val newSearchTerms = searchTerms != lastSearchTerms
        if (searchInProgress && !newSearchTerms) {
            return SearchResult.FAIL("Identical search already in progress")
        }

        searchJob?.cancel()
        lastSearchTerms = searchTerms


        return try {
            locations = locationSearchProvider.searchLocationsByKeywords(searchTerms)
            SearchResult.SUCCESS()
        } catch (e: Exception) {
            errorMessage = e.toString()
            SearchResult.FAIL(errorMessage ?: "Problem with search provider")
        }

    }
}