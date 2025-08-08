package com.jontypine.mapmissionary.interfaces

import com.jontypine.mapmissionary.data.LatLong

interface LatLongProvider {
    suspend fun getLatLongFromGridRef(gridRef: String): LatLong?
}