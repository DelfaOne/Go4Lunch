package com.fadel.go4lunch.data.datasource

import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponses
import com.fadel.go4lunch.data.pojo.nearbyplace.details.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NearbyPlacesDataSource {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): NearbyResponses

//    https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJMxOToCnq9EcREuoX_SlbSJY&key=AIzaSyCod1va_8xcRFf8epc5HkFkDY1ZUu6bkeo
    @GET("maps/api/place/details/json/{place_id}")
    suspend fun getDetailPlaces(
        @Path("place_id") placeId: String,
        @Query("key") key: String
    ): DetailResponse
}
