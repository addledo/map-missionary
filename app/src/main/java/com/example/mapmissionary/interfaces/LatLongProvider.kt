package com.example.mapmissionary.interfaces

import com.example.mapmissionary.data.LatLong

interface LatLongProvider {
    suspend fun getLatLongFromGridRef(gridRef: String): LatLong?
}