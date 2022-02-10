package com.primelab.nearBase.data.networks.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("jwt_access_token")
    val accessToken: String,
    @SerializedName("jwt_id_token")
    val idToken: String,
    @SerializedName("jwt_refresh_token")
    val refreshToken: String,
    @SerializedName("user_info")
    val userInfo: UserResponse
)

