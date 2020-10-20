package com.justcode.bubblepop.model

data class AuthResponse(
	val message: String? = null,
	val user: User? = null,
	val status: Boolean? = null
)

data class User(
	val profilePhotoUrl: String? = null,
	val role: String? = null,
	val updatedAt: String? = null,
	val name: String? = null,
	val createdAt: String? = null,
	val emailVerifiedAt: Any? = null,
	val id: Int? = null,
	val email: String? = null
)

