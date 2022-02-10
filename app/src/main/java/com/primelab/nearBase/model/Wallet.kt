package com.primelab.nearBase.model

data class Wallet(
    val id: Int,
    val address: String,
    val name: String,
    val selected: Boolean
)

fun Wallet.toDomainModel() = kotlin.run {
    Wallet(
        id = id,
        name = name,
        address = address,
        selected = selected
    )
}

