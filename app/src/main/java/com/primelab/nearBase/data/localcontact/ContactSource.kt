package com.primelab.nearBase.data.localcontact

import com.primelab.nearBase.model.Contact


interface ContactSource {
    suspend fun getAllContactWithEmail(userId: String): List<Contact>
}