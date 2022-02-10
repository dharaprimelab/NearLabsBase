package com.primelab.nearBase.data.networks

import com.google.gson.JsonObject
import com.primelab.nearBase.data.networks.request.AddWalletRequest
import com.primelab.nearBase.data.networks.request.LoginRequest
import com.primelab.nearBase.data.networks.request.UserCreateRequest
import com.primelab.nearBase.data.networks.response.*

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("api/users")
    suspend fun sampleGet(): JsonObject

    @GET("users/{user_id}")
    suspend fun getUserProfile(@Path("user_id") userId: String): UserProfileResponse

    @PUT("users/{user_id}")
    suspend fun modifyUser(@Path("user_id") userId: String, @Body request: UserCreateRequest): UserInfoResponse

    @POST("login")
    suspend fun login(@Body walletName : LoginRequest): LoginResponse

}