package com.primelab.nearBase.repository

import com.google.gson.Gson
import com.primelab.nearBase.model.Contact
import com.primelab.nearBase.data.networks.*
import com.primelab.nearBase.data.localcontact.ContactSource
import com.primelab.nearBase.data.networks.Api
import com.primelab.nearBase.data.networks.request.LoginRequest
import com.primelab.nearBase.data.networks.request.RefreshTokenRequest
import com.primelab.nearBase.data.networks.request.UserCreateRequest
import com.primelab.nearBase.data.preference.SharePrefs
import com.primelab.nearBase.extensions.safeCall
import com.primelab.nearBase.extensions.safeCallWithHttpError
import com.primelab.nearBase.model.toDomain


class Repository(
    private val api: Api,
    private val contactApi: ContactApi,
    private val userApi: UserApi,
    private val loginApi: LoginApi,
    private val sharePrefs: SharePrefs,
    private val localContact: ContactSource
) {

    fun isLoggedIn() = sharePrefs.accessToken.isNotEmpty()

    suspend fun createUser(
        name: String,
        walletId: String,
        phone: String,
        email: String
    ) =
        safeCallWithHttpError {
            val request = UserCreateRequest(
                fullName = name,
                walletName = walletId,
                phone = phone,
                email = email
            )
            val dtoResponse = userApi.createUser(request).apply {
                sharePrefs.userId = userInfo.id
                sharePrefs.userName = userInfo.fullName ?: ""
                sharePrefs.walletName = walletId
                sharePrefs.accessToken = accessToken
                sharePrefs.idToken = idToken
                sharePrefs.refreshToken = refreshToken
                sharePrefs.userInfo = Gson().toJson(userInfo)
            }
            dtoResponse.userInfo.toDomain()
        }

    suspend fun login(walletName: String) =
        safeCallWithHttpError {
            val request = LoginRequest(walletName = walletName)
            val dtoResponse = loginApi.login(request).apply {
                sharePrefs.loginType = type
                sharePrefs.walletName = walletName
            }
            dtoResponse
        }

    suspend fun verifyLogin(walletName: String, nonce: String) =
        safeCallWithHttpError {
            val request = LoginRequest(
                walletName = walletName,
                nonce = nonce
            )
            val dtoResponse = loginApi.verifyLogin(request).apply {
                sharePrefs.userId = userInfo.id
                sharePrefs.userName = userInfo.fullName ?: ""
                sharePrefs.walletName = walletName
                sharePrefs.accessToken = accessToken
                sharePrefs.idToken = idToken
                sharePrefs.refreshToken = refreshToken
                sharePrefs.userInfo = Gson().toJson(userInfo)
            }
        }



    suspend fun postLocalContact(contacts: List<Contact>) = safeCall {
        val request = localContact.getAllContactWithEmail(sharePrefs.userId)
        contactApi.importContact(contacts)
    }

    suspend fun postAddLocalContact(contacts: Contact) = safeCall {
        contactApi.addContact(contacts.apply { owner_id = sharePrefs.userId })
    }

    suspend fun getLocalContact() = safeCall {
        localContact.getAllContactWithEmail(sharePrefs.userId)

    }




    suspend fun getUserProfile(userId: String) = safeCall {
        val dtoResponse = api.getUserProfile(userId)
        dtoResponse.userInfo.toDomain()
    }

    suspend fun modifyUser(userId: String, currentPhone: String, currentEmail: String) = safeCall {
        val dToUser = UserCreateRequest(
            sharePrefs.userName,
            sharePrefs.walletName,
            currentPhone,
            currentEmail
        )
        api.modifyUser(sharePrefs.userId, dToUser)
    }

    suspend fun refreshToken(walletId: String, refreshToken: String) = safeCallWithHttpError {
        val dtoRequest = RefreshTokenRequest(walletId, refreshToken)
        userApi.refreshToken(dtoRequest)
    }

    fun getUserId() = sharePrefs.userId
}