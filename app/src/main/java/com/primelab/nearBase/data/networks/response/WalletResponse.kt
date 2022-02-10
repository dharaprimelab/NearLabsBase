package com.primelab.nearBase.data.networks.response

import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("list")
    val wallets: List<Wallet>? = null
)

data class Wallet(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("selected")
    val selected: Boolean? = null
)