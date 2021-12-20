package com.fadel.go4lunch.data.pojo.nearbyplace

import com.google.gson.annotations.SerializedName

data class OpeningHours(

    @field:SerializedName("open_now")
    val openNow: Boolean? = null
)