package com.fadel.go4lunch.data.pojo.nearbyplace.details

import com.google.gson.annotations.SerializedName

data class PeriodsItem(

	@field:SerializedName("close")
	val close: Close? = null,

	@field:SerializedName("open")
	val open: Open? = null
)