package com.primelab.nearBase.data.networks.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("walletName")
    val walletName:String,
    @SerializedName("refreshToken")
    val refreshToken:String
)
