package com.fadel.go4lunch.data.pojo.autocomplete

import com.google.gson.annotations.SerializedName

data class AutocompleteResponse(

    @field:SerializedName("predictions")
    val predictions: List<PredictionsItem> = emptyList(),

    @field:SerializedName("status")
    val status: String? = null
)