package com.example.mapmissionary.utilities

import com.example.mapmissionary.GeoDojoJsonParser
import com.example.mapmissionary.GeoDojoUrlConfig
import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.interfaces.LatLongProvider
import com.example.mapmissionary.interfaces.LocationSearchProvider
import javax.inject.Inject

class GeoDojoService @Inject constructor(private val networkRepository: NetworkRepository) :
    GridRefProvider, LatLongProvider, LocationSearchProvider {

    override suspend fun searchLocationsByKeywords(keyWords: String): List<Location> {
        val validatedKeyWords = PostcodeValidator.validate(keyWords)
        val url = GeoDojoUrlConfig.getGridFromKeywordsUrl(validatedKeyWords)
        val locationsJSON = networkRepository.fetchData(url) ?: return listOf()
        return GeoDojoJsonParser.parseSearchJSON(locationsJSON)
    }

    override suspend fun getGridFromLatLong(latLong: LatLong?): String? {
        if (latLong == null) {
            return null
        }
        val url = GeoDojoUrlConfig.getGridFromLatLongUrl(latLong)
        val resultJSON = networkRepository.fetchData(url) ?: return "Not found"
        return GeoDojoJsonParser.parseGridApiJson(resultJSON, "grid")
    }

    override suspend fun getLatLongFromGridRef(gridRef: String): LatLong? {
        if (gridRef.isBlank()) {
            return null
        }
        val url = GeoDojoUrlConfig.getLatLongFromGridUrl(gridRef)
        val resultJSON = networkRepository.fetchData(url) ?: return null

        val latLongStr = GeoDojoJsonParser.parseGridApiJson(resultJSON, "latlng")
            ?: return null
        return LatLong(
            lat = latLongStr.substringBefore(" ").toDouble(),
            long = latLongStr.substringAfter(" ").toDouble()
        )
    }
}