package com.example.mapmissionary.extensions


fun String.isGridRef(): Boolean {
    val gridRefRegex = Regex("[a-zA-Z]{2}\\d{1,5}\\s?\\d{1,5}")

    if (this.length > 13) {
        return false
    }

    return this.matches(gridRefRegex)
}