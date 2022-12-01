package com.fadel.go4lunch.data.repository.location

import android.location.Location
import com.fadel.go4lunch.data.repository.location.LocationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationUtils @Inject constructor(){

    fun distanceBetween(locationEntity1: LocationEntity,locationEntity2: LocationEntity): Float {
        val location1 = Location("")
        location1.latitude = locationEntity1.lat
        location1.longitude = locationEntity1.long

        val location2 = Location("")
        location2.latitude = locationEntity2.lat
        location2.longitude = locationEntity2.long

        return location1.distanceTo(location2)
    }
}
