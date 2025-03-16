package com.example.mapmissionary

import android.util.Log
import com.example.mapmissionary.data.LatLong

object GeoDojoUrlConfig {
    private const val SEARCH_BASE_URL = "https://api.geodojo.net/locate/find"
    private const val GRID_BASE_URL = "https://api.geodojo.net/locate/grid?type=grid&q="
    private const val MAX_RESULTS = 10

    fun getGridFromKeywordsUrl(keyWords: String): String {
        // Trim and replace any blocks of whitespace with "+"
        val locationArgs = keyWords.trim().replace(Regex("\\s+"), "+")
        val resultType = "grid"

        val url = "${SEARCH_BASE_URL}?q=${locationArgs}&max=$MAX_RESULTS&type=$resultType"
        Log.i("url", url)
        return url
    }

    fun getGridFromLatLongUrl(latLong: LatLong): String {
        val url = "$GRID_BASE_URL${latLong.lat}+${latLong.long}"
        Log.i("url", url)
        return url
    }
}