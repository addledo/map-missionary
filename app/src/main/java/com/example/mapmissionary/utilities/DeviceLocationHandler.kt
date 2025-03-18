package com.example.mapmissionary.utilities

import android.annotation.SuppressLint
import android.content.Context
import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.extensions.hasLocationPermissions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

class DeviceLocationHandler(private val context: Context) {
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

        if (currentLocation == null) {
            throw Exception(
                "There was a problem getting your location. Are you sure you have location services turned on?"
            )
        }

        return Location(
            latLong = LatLong(currentLocation.latitude, currentLocation.longitude)
        )
    }

    fun hasPermission(): Boolean {
        return context.hasLocationPermissions()
    }
}