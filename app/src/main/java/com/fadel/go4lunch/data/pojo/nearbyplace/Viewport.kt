package com.fadel.go4lunch.data.pojo.nearbyplace

import com.google.gson.annotations.SerializedName

data class Viewport(

    @field:SerializedName("southwest")
    val southwest: Southwest? = null,

    @field:SerializedName("northeast")
    val northeast: Northeast? = null
)