package com.primelab.nearBase.data.networks.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T
)

data class MessageResponse (
    @SerializedName("message")
    val message: String,
)