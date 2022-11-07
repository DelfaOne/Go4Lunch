package com.fadel.go4lunch.data.pojo.autocomplete

import com.google.gson.annotations.SerializedName

data class TermsItem(

    @field:SerializedName("offset")
    val offset: Int? = null,

    @field:SerializedName("value")
    val value: String? = null
)