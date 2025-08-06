package com.example.mapmissionary.view_models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.extensions.isGridRef
import com.example.mapmissionary.extensions.isLatLong
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.interfaces.LocationSearchProvider
import com.example.mapmissionary.utilities.OsgbConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import uk.gov.dstl.geo.osgb.NationalGrid
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

    suspend fun runLocationSearch(searchTerms: String): Boolean {
        if (searchTerms.isBlank()) {
            return false
        }

        if (searchTerms.isLatLong()) {
            val lat: Double
            val long: Double

            try {
                lat = searchTerms.substringBefore(',').toDouble()
                long = searchTerms.substringAfter(',').toDouble()
            } catch (_: NumberFormatException) {
                return false
            }

            val latLong = LatLong(lat, long)
            val gridRef = gridRefProvider.getGridFromLatLong(latLong)
            locations = listOf(Location(latLong = latLong, gridRef = gridRef))
            return true
        }

        val searchInProgress = searchJob?.isActive ?: false
        val newSearchTerms = searchTerms != lastSearchTerms
        if (searchInProgress && !newSearchTerms) {
            return false
        }

        searchJob?.cancel()
        lastSearchTerms = searchTerms

        Log.d("GR", "Search terms == gridRef? ${searchTerms.isGridRef()}")
        if (searchTerms.isGridRef()) {
            val gridRef = searchTerms.uppercase().replace("\\w", "")
            locations = listOf(Location(gridRef = gridRef))
            return true
        }

        return try {
            locations = locationSearchProvider.searchLocationsByKeywords(searchTerms)
            true
        } catch (e: Exception) {
            errorMessage = e.toString()
            false
        }

    }
}