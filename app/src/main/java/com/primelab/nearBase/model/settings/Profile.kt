package com.primelab.nearBase.model.settings

import com.primelab.nearBase.model.Wallet

data class Profile(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val wallet: Wallet,
    val security: Security,
)