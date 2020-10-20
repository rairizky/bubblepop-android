package com.justcode.bubblepop.model

import com.google.gson.annotations.SerializedName

data class PromoResponse(

	@field:SerializedName("promo")
	val promo: List<PromoItem?>? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class PromoItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("promo_end")
	val promoEnd: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("promo_start")
	val promoStart: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
