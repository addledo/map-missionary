package com.example.mapmissionary.data

import com.example.mapmissionary.interfaces.GridRefProvider

data class LatLong(val lat: Double, val long: Double) {


    override fun toString(): String {
        val decimalPlacesForOneMeterAccuracy = 5
        val format = "%.${decimalPlacesForOneMeterAccuracy}f"

        // Rounds to specified DP and then removes trailing 0s
        val latStr = format.format(lat).trimEnd {it == '0'}
        val longStr = format.format(long).trimEnd {it == '0'}

        return "$latStr, $longStr"
    }

    suspend fun getGridRef(gridRefProvider: GridRefProvider): String {
        return gridRefProvider.getGridFromLatLong(this)
    }
}