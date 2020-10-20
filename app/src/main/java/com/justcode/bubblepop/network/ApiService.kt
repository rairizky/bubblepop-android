package com.justcode.bubblepop.network

import com.justcode.bubblepop.model.AuthResponse
import com.justcode.bubblepop.model.MenuResponse
import com.justcode.bubblepop.model.PromoResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("header")
    fun getHeader() : Call<MenuResponse>

    @GET("promo")
    fun getPromo() : Call<PromoResponse>

    @GET("menu")
    fun getMenu() : Call<MenuResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<AuthResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun postRegister(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<AuthResponse>
}