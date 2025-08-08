package com.jontypine.mapmissionary.interfaces

import com.jontypine.mapmissionary.data.Extra
import com.jontypine.mapmissionary.data.LatLong

interface ExtrasProvider {
    suspend fun getExtras(latLong: LatLong?, extrasToGet: List<Extra>): List<Pair<String, String>>
}