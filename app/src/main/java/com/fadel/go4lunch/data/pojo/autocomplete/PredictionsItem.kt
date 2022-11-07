package com.fadel.go4lunch.data.pojo.autocomplete

import com.google.gson.annotations.SerializedName

data class PredictionsItem(

    @field:SerializedName("reference")
    val reference: String? = null,

    @field:SerializedName("types")
    val types: List<String>? = null,

    @field:SerializedName("matched_substrings")
    val matchedSubstrings: List<MatchedSubstringsItem> = emptyList(),

    @field:SerializedName("terms")
    val terms: List<TermsItem> = emptyList(),

    @field:SerializedName("structured_formatting")
    val structuredFormatting: StructuredFormatting? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("place_id")
    val placeId: String? = null
)