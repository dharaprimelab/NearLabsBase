package com.primelab.nearBase.viewmodel


import androidx.lifecycle.ViewModel
import com.primelab.nearBase.data.preference.SharePrefs
import com.primelab.nearBase.extensions.resultFlow
import com.primelab.nearBase.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository, sharePrefsRepository: SharePrefs) : ViewModel() {

    var prefs :SharePrefs = sharePrefsRepository
    var currentEmail: String = sharePrefsRepository.currentEmail
    var currentPhone: String = sharePrefsRepository.currentPhone
    var usesPhone: Boolean = false
    var walletName = sharePrefsRepository.walletName
    val loginType = sharePrefsRepository.loginType
    val loggedIn = sharePrefsRepository.accessToken.isNotEmpty() || sharePrefsRepository.accessToken.isNotBlank()


    fun createUser(name: String, walletId: String, claimNFTID: String? = null) = resultFlow {
        repository.createUser(name, walletId, currentPhone, currentEmail)
    }

    fun loginUser(walletName: String) = resultFlow {
        repository.login(walletName)
    }

    fun verifyUser(walletName: String, nonce: String) = resultFlow {
        repository.verifyLogin(walletName, nonce)
    }

    fun updateUser(userId: String) = resultFlow {
        repository.modifyUser(userId, currentPhone, currentEmail)
    }

}