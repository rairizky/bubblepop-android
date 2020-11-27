package com.justcode.bubblepop.model

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("transaction")
	val transaction: List<TransactionCartItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class TransactionCartItem(

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("order_id")
	val orderId: Int? = null,

	@field:SerializedName("mount")
	val mount: Int? = null,

	@field:SerializedName("menu_id")
	val menuId: Int? = null
)
