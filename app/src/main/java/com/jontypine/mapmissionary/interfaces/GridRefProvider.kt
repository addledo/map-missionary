package com.jontypine.mapmissionary.interfaces

import com.jontypine.mapmissionary.data.LatLong

interface GridRefProvider {
    suspend fun getGridFromLatLong(latLong: LatLong?): String?
}