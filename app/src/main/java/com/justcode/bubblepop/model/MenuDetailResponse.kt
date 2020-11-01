package com.justcode.bubblepop.model

data class MenuDetailResponse(
	val menu: MenuDetailItem? = null,
	val status: Boolean? = null
)

data class MenuDetailItem(
	val image: String? = null,
	val categoryId: Int? = null,
	val updatedAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val createdAt: String? = null,
	val id: Int? = null,
	val priceL: Int? = null,
	val priceM: Int? = null,
	val status: String? = null
)

