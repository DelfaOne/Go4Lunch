package com.fadel.go4lunch.data.pojo.nearbyplace

import com.google.gson.annotations.SerializedName

data class Southwest(

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)