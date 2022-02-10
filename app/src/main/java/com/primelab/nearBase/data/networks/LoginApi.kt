package com.primelab.nearBase.data.networks

import com.primelab.nearBase.data.networks.request.LoginRequest
import com.primelab.nearBase.data.networks.response.LoginResponse
import com.primelab.nearBase.data.networks.response.VerifyLoginResponse
import retrofit2.http.*

interface LoginApi {
    @POST("login")
    suspend fun login(@Body walletName : LoginRequest): LoginResponse

    @POST("login/verify")
    suspend fun verifyLogin(@Body walletName : LoginRequest): VerifyLoginResponse
}