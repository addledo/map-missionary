package com.example.mapmissionary.navigation

sealed class Screen(val route: String) {
    data object Search: Screen("search")
    data object CurrentLocation: Screen("current_location")
    data object LocationDetails: Screen("location_details")
}