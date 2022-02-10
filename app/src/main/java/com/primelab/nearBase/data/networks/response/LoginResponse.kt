package com.primelab.nearBase.data.networks.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("message")
    val message: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("type")
    val type: String

)