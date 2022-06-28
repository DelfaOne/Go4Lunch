package com.fadel.go4lunch.data.repository

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
){

    @SuppressLint("MissingPermission")
    fun getLocationFlow() = callbackFlow {
        // A new Flow is created. This code executes in a coroutine!

        // 1. Create callback and add elements into the flow
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                trySend(result.lastLocation)
            }
        }
        // 2. Register the callback to get location updates by calling requestLocationUpdates
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest
                .create()
                .setSmallestDisplacement(50F)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(30_000),
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e) // in case of error, close the Flow
        }

        // 3. Wait for the consumer to cancel the coroutine and unregister
        // the callback. This suspends the coroutine until the Flow is closed.
        awaitClose {
            // Clean up code goes here
            fusedLocationProviderClient.removeLocationUpdates(callback)
        }
    }
}