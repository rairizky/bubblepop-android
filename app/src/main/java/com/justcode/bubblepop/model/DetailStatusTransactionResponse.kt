package com.justcode.bubblepop.model

import com.google.gson.annotations.SerializedName

data class DetailStatusTransactionResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("cashier")
	val cashier: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("paid")
	val paid: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("transaction")
	val transaction: List<TransactionDetailItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class TransactionDetailItem(

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
