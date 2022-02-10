package com.primelab.nearBase.data.networks

import com.primelab.nearBase.data.networks.request.RefreshTokenRequest
import com.primelab.nearBase.data.networks.request.UserCreateRequest
import com.primelab.nearBase.data.networks.response.RefreshTokenResponse
import com.primelab.nearBase.data.networks.response.UserInfoResponse
import com.primelab.nearBase.data.networks.response.UserProfileResponse
import retrofit2.http.*

interface UserApi {
    @GET("users/{user_id}")
    suspend fun getUserProfile(@Path("user_id") userId: String): UserProfileResponse

    @PUT("{user_id}")
    suspend fun modifyUser(@Path("user_id") userId: String): UserInfoResponse

    @DELETE("{user_id}")
    suspend fun deleteUser(@Path("user_id") userId: String): UserInfoResponse

    @GET("user/{user_id}/resend_code")
    suspend fun resendCode(@Path("user_id") userId: String): UserInfoResponse

    @POST("user/create")
    suspend fun createUser(@Body request: UserCreateRequest): UserInfoResponse

    @POST("user/suggest/?walletName=moisesmarques.near&suggestionCount=10")
    suspend fun suggestWalletName(@Body request: UserCreateRequest): UserInfoResponse

    @POST("users/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): RefreshTokenResponse
}