package com.justcode.bubblepop.network

import com.justcode.bubblepop.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("header")
    fun getHeader() : Call<MenuResponse>

    @GET("promo")
    fun getPromo() : Call<PromoResponse>

    @GET("menu")
    fun getMenu() : Call<MenuResponse>

    @GET("menu/{id}")
    fun getDetailMenu(
        @Path("id") id : String
    ) : Call<MenuDetailResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<AuthResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<AuthResponse>

    // add menu to cart
    @FormUrlEncoded
    @POST("transaction/{userId}/cart/add")
    fun postAddMenuToCart(
        @Path("userId") userId : String,
        @Field("size") size : String,
        @Field("mount") mount : Int,
        @Field("menu_id") menu_id : Int
    ) : Call<MessageResponse>

    // transaction cart
    @GET("transaction/{userId}/cart")
    fun getListCart(
        @Path("userId") userId: String
    ) : Call<CartResponse>

    // checkout cart
    @POST("transaction/{userId}/cart/checkout")
    fun postCheckout(
        @Path("userId") userId: String
    ) : Call<MessageResponse>

    // transaction pending
    @GET("transaction/{userId}/pending")
    fun getListPending(
        @Path("userId") userId: String
    ) : Call<PendingFinishResponse>

    // transaction finish
    @GET("transaction/{userId}/finish")
    fun getListFinish(
        @Path("userId") userId: String
    ) : Call<PendingFinishResponse>

}