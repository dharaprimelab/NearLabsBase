package com.primelab.nearBase.data.networks.request

import com.google.gson.annotations.SerializedName

data class UserCreateRequest(
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("walletName")
    val walletName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?
)