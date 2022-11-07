package com.fadel.go4lunch.data.pojo.autocomplete

import com.google.gson.annotations.SerializedName

data class MatchedSubstringsItem(

    @field:SerializedName("offset")
    val offset: Int? = null,

    @field:SerializedName("length")
    val length: Int? = null
)