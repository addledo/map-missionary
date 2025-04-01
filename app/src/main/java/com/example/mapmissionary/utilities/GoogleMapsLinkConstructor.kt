package com.example.mapmissionary.utilities

import android.util.Log
import com.example.mapmissionary.data.LatLong

object GoogleMapsLinkConstructor {
    fun getLinkFromLatLong(coordinates: LatLong?): String? {
        if (coordinates == null) {
            return null
        }
        val baseUrl = "https://www.google.com/maps/place/"
        val url = baseUrl + coordinates.toString().replace(Regex("\\s"), "")
        Log.d("url", url)
        return url
    }
}