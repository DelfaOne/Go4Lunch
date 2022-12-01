package com.fadel.go4lunch.domain.location

import android.location.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDefaultLocationUseCase @Inject constructor() {

    fun invoke() = Location("default").apply {
        latitude = 50.63676868035896
        longitude = 3.0700331845533104
    }

}