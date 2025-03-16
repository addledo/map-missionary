package com.example.mapmissionary.data

data class LatLong(val lat: Double, val long: Double) {

    override fun toString(): String {
        val decimalPlacesForOneMeterAccuracy = 5
        val dpFormat = "%.${decimalPlacesForOneMeterAccuracy}f"

        // Rounds to n dp
        var latStr = dpFormat.format(lat)
        var longStr = dpFormat.format(long)

        // Removes trailing 0s to remove redundant information without reducing accuracy
        // This could potentially imply imprecision but I have decided to format this
        // way in order to make the values easier to read and write
        latStr = latStr.trimEnd { it == '0' }
        longStr = longStr.trimEnd { it == '0' }

        // Removes trailing decimal point if there are no digits following
        // e.g. "10." becomes "10"
        // Must be done separately to the previous operation or 10.0 becomes 1
        latStr = latStr.trimEnd { it == '.' }
        longStr = longStr.trimEnd { it == '.' }

        return "$latStr, $longStr"
    }
}