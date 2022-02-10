package com.primelab.nearBase.data.networks.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("walletName")
    val walletName : String?,
    @SerializedName("nonce")
    val nonce: String? = null
)
