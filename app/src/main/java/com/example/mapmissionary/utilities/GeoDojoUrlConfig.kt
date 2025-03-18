package com.example.mapmissionary.utilities

import android.util.Log
import com.example.mapmissionary.data.LatLong

object GeoDojoUrlConfig {
    private const val SEARCH_BASE_URL = "https://api.geodojo.net/locate/find?"
    private const val GRID_ENDPOINT_URL = "https://api.geodojo.net/locate/grid?"

    private const val NEAREST_INFO_BASE_URL = "https://api.geodojo.net/locate/nearest"
    private const val MAX_RESULTS = 10

    fun getGridFromKeywordsUrl(keyWords: String): String {
        var locationArgs = keyWords.trim()

        // Blocks of whitespace of any size between words will be replaced with "+"
        // e.g.    "foo bar      baz"  -->  "foo+bar+baz"
        locationArgs = locationArgs.replace(Regex("\\s+"), "+")

        val urlBuilder = StringBuilder(SEARCH_BASE_URL)
        urlBuilder.append("type=grid")

        urlBuilder.append("&max=")
        urlBuilder.append(MAX_RESULTS)

        urlBuilder.append("&q=")
        urlBuilder.append(locationArgs)

        val url = urlBuilder.toString()
        Log.i("url", url)
        return url
    }

    fun getGridFromLatLongUrl(latLong: LatLong): String {
        val urlBuilder = StringBuilder(GRID_ENDPOINT_URL)

        urlBuilder.append("type=grid")

        urlBuilder.append("&q=")
        urlBuilder.append(latLong.lat)
        urlBuilder.append("+")
        urlBuilder.append(latLong.long)

        val url = urlBuilder.toString()
        Log.i("url", url)
        return url
    }

    fun getLatLongFromGridUrl(gridRef: String): String {
        val urlBuilder = StringBuilder(GRID_ENDPOINT_URL)

        urlBuilder.append("type=latlng")

        urlBuilder.append("&q=")
        urlBuilder.append(gridRef)

        val url = urlBuilder.toString()
        Log.i("url", url)
        return url
    }


    fun getExtraDetailsFromGridRefUrl(gridRef: String): String {
        val urlBuilder = StringBuilder(NEAREST_INFO_BASE_URL)
        urlBuilder.append("?q=")
        urlBuilder.append(gridRef)

        // Fields to request from the API
        urlBuilder.append("&type[]=")
        urlBuilder.append("police-force-area")

        urlBuilder.append("&type[]=")
        urlBuilder.append("county-unitary-authority")

        urlBuilder.append("&type[]=")
        urlBuilder.append("postcode-centre")

        val url = urlBuilder.toString()
        Log.i("url", url)
        return url
    }
}