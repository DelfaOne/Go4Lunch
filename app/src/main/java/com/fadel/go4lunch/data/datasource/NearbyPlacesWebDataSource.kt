package com.fadel.go4lunch.data.datasource

import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponses
import com.fadel.go4lunch.data.pojo.nearbyplace.details.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NearbyPlacesWebDataSource {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): NearbyResponses

    @GET("maps/api/place/details/json")
    suspend fun getDetailPlaces(
    @Query("place_id") placeId: String,
    @Query("key") key: String
    ): DetailResponse
}
