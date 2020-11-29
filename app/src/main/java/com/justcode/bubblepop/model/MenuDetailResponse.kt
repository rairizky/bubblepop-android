package com.justcode.bubblepop.model

data class MenuDetailResponse(
	val menu: MenuDetailItem? = null,
	val status: Boolean? = null
)

data class MenuDetailItem(
	val image: String? = null,
	val category_id: Int? = null,
	val updated_at: String? = null,
	val name: String? = null,
	val description: String? = null,
	val created_at: String? = null,
	val id: Int? = null,
	val price_l: Int? = null,
	val price_m: Int? = null,
	val status: String? = null
)

