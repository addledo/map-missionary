package com.example.mapmissionary.data

data class Location(
    var address: String? = null,
    var gridRef: String? = null,
    var coordinates: LatLong? = null
) {

    // Implemented for clarity
    companion object {
        fun empty(): Location {
            return Location()
        }
    }
}