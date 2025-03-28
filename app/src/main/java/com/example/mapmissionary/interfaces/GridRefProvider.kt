package com.example.mapmissionary.interfaces

import com.example.mapmissionary.data.LatLong

interface GridRefProvider {
    suspend fun getGridFromLatLong(latLong: LatLong?): String?
}