package com.fadel.go4lunch.data.pojo.nearbyplace

import com.google.gson.annotations.SerializedName

data class NearbyResponses(

    @field:SerializedName("results")
    val results: List<NearbyResponse?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)