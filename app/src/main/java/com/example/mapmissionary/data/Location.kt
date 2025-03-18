package com.example.mapmissionary.data

data class Location(
    var address: String? = null,
    var gridRef: String? = null,
    var latLong: LatLong? = null,
    val extras: List<Pair<String, String>> = listOf()
)