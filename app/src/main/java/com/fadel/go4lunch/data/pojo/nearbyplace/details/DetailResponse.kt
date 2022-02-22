package com.fadel.go4lunch.data.pojo.nearbyplace.details

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)