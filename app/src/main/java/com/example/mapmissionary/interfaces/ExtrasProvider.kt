package com.example.mapmissionary.interfaces

import com.example.mapmissionary.data.Extra
import com.example.mapmissionary.data.LatLong

interface ExtrasProvider {
    suspend fun getExtras(latLong: LatLong?, extrasToGet: List<Extra>): List<Pair<String, String>>
}