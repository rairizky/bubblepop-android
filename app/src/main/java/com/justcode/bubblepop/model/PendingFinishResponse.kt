package com.justcode.bubblepop.model

import com.google.gson.annotations.SerializedName

data class PendingFinishResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("transaction")
	val transaction: List<TransactionPendingFinishItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class TransactionPendingFinishItem(

	@field:SerializedName("total")
	val total: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

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

	@field:SerializedName("status")
	val status: String? = null
)
