package com.fadel.go4lunch.data.datasource

import com.fadel.go4lunch.data.pojo.autocomplete.AutocompleteResponse
import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponses
import com.fadel.go4lunch.data.pojo.nearbyplace.details.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AutocompleteWebDataSource {
    @GET("maps/api/place/autocomplete/json")
    suspend fun getAutocomplete(
        @Query("input") input: String,
        @Query("types") types: String,
        @Query("location") location: String?,
        @Query("radius") radius: String,
        @Query("key") key: String
    ): AutocompleteResponse
}
