package com.example.mapmissionary.data

data class LatLong(val lat: Double, val long: Double) {


    override fun toString(): String {
        val decimalPlacesForOneMeterAccuracy = 5
        val format = "%.${decimalPlacesForOneMeterAccuracy}f"

        // Rounds to specified DP and then removes trailing 0s
        // Also removes the decimal point if there are no numbers after it
        val latStr = format.format(lat).trimEnd { it == '0' || it == '.' }
        val longStr = format.format(long).trimEnd { it == '0' || it == '.' }

        return "$latStr, $longStr"
    }
}