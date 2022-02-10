package com.primelab.nearBase.model

import com.primelab.nearBase.data.networks.response.UserResponse


data class User(
    val id: String,
    val walletId: String,
    val name: String,
    val email: String,
    val phone: String,
    val verified: Boolean
)

fun UserResponse.toDomain() = User(
    id = id,
    walletId = walletId,
    name = fullName.orEmpty(),
    email = email.orEmpty(),
    phone = phone.orEmpty(),
    verified = verified ?: false
)