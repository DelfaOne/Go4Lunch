package com.fadel.go4lunch.data.repository.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
){

    private var lastKnownLocation : Location? = null

    @SuppressLint("MissingPermission")
    fun getLocationFlow(): Flow<Location> = callbackFlow {
        // A new Flow is created. This code executes in a coroutine!
        val lastLocationTask = fusedLocationProviderClient.lastLocation

        if (lastLocationTask.isSuccessful) {
            val lastLocation = lastLocationTask.result
            if (lastLocation != null) {
                trySend(lastLocation)
            }
        } else {
            lastKnownLocation?.let {
                trySend(it)
            }
        }

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
    }.onEach {
        lastKnownLocation = it
    }
}