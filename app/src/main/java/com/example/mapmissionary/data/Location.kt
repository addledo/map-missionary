package com.example.mapmissionary.data

data class Location(
    var address: String? = null,
    var gridRef: String? = null,
    var latLong: LatLong? = null,
    val town: String? = null,
    val extras: MutableList<Pair<String, String>> = mutableListOf()
) {
    fun addExtra(pair: Pair<String, String>) {
        extras.add(pair)
    }
}