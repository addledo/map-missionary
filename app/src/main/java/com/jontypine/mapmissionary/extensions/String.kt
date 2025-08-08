package com.jontypine.mapmissionary.extensions


fun String.isGridRef(): Boolean {
    val gridRefRegex = Regex("[a-zA-Z]{2}\\s?\\d{1,5}\\s?\\d{1,5}")

    val normalisedGridRef = this.replace(Regex("\\s+"), "")

    if (normalisedGridRef.length > 14 || normalisedGridRef.length % 2 != 0) {
        return false
    }

    return normalisedGridRef.matches(gridRefRegex)
}

fun String.isLatLong(): Boolean {
    // Regex from https://stackoverflow.com/a/18690202
    val latLongRegex = Regex("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)\$")

    return this.trim().matches(latLongRegex)
}