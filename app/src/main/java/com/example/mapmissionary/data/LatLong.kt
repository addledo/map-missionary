package com.example.mapmissionary.data

import com.example.mapmissionary.interfaces.GridRefProvider

class LatLong(val lat:Float, val long: Float) {

    suspend fun getGridRef(gridRefProvider: GridRefProvider): String {
        return gridRefProvider.getFromLatLong(this)
    }

}