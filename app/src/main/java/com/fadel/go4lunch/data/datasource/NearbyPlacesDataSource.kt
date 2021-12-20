package com.fadel.go4lunch.data.datasource

import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponse
import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponses
import retrofit2.http.GET
import retrofit2.http.Query

interface NearbyPlacesDataSource {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("key") key: String
    ) : NearbyResponses
}
