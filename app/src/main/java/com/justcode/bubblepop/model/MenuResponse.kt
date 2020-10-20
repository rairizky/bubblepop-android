package com.justcode.bubblepop.model

import com.google.gson.annotations.SerializedName

data class MenuResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("menu")
	val menu: List<MenuItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class MenuItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("price_l")
	val priceL: Int? = null,

	@field:SerializedName("price_m")
	val priceM: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
