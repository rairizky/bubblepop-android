package com.justcode.bubblepop.model

import com.google.gson.annotations.SerializedName

data class MessageResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
