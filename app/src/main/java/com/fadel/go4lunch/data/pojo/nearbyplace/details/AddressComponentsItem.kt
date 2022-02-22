package com.fadel.go4lunch.data.pojo.nearbyplace.details

import com.google.gson.annotations.SerializedName

data class AddressComponentsItem(

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("long_name")
	val longName: String? = null
)