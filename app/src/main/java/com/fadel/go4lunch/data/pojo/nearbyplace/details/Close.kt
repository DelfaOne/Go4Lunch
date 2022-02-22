package com.fadel.go4lunch.data.pojo.nearbyplace.details

import com.google.gson.annotations.SerializedName

data class Close(

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("day")
	val day: Int? = null
)