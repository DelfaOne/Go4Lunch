package com.fadel.go4lunch.data.pojo.autocomplete

import com.google.gson.annotations.SerializedName

data class StructuredFormatting(

    @field:SerializedName("main_text_matched_substrings")
    val mainTextMatchedSubstrings: List<MainTextMatchedSubstringsItem> = emptyList(),

    @field:SerializedName("secondary_text")
    val secondaryText: String? = null,

    @field:SerializedName("main_text")
    val mainText: String? = null
)