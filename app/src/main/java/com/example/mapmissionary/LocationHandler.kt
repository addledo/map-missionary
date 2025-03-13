package com.example.mapmissionary

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

class LocationHandler(private val context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Location? {
        if (!hasPermission()) {
            return null
        }

        val currentLocation = fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).await()


        return Location(
            coordinates = "${currentLocation.latitude}, ${currentLocation.longitude}"
        )

    }

    fun hasPermission(): Boolean {
        return context.hasLocationPermissions()
    }
}