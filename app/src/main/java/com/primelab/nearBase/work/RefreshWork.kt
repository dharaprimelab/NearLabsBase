package com.primelab.nearBase.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.primelab.nearBase.data.preference.SharePrefs
import com.primelab.nearBase.extensions.State
import com.primelab.nearBase.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class RefreshWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val preferences: SharePrefs,
    private val repository: Repository
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const
        val TOKEN_REFRESHED = "TokenRefreshed"
        val REFRESH_WORK = "REFRESH_WORK"
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                if (repository.isLoggedIn()) {
                    val result = attemptRefreshToken(preferences.walletName, preferences.refreshToken)
                    Timber.d("RefreshResult ${result}")
                    if (!result.getBoolean(TOKEN_REFRESHED, false)) {
                        Result.failure(result)
                    } else {
                        Result.success(result)
                    }
                } else {
                    Result.failure()
                }
            } catch (e: Exception) {
                Timber.e(e)
                Result.failure()
            }
        }


    }

    private suspend fun attemptRefreshToken(walletId: String, refreshToken: String): Data {

        return when (val result = repository.refreshToken(walletId, refreshToken)) {

            is State.GenericError -> {
                workDataOf(Pair(TOKEN_REFRESHED, false))
            }
            is State.HttpError -> {
                workDataOf(Pair(TOKEN_REFRESHED, false))
            }

            is State.Success -> {
                preferences.accessToken = result.value.accessToken
                preferences.idToken = result.value.idToken
                preferences.refreshToken = result.value.refreshToken
                workDataOf(Pair(TOKEN_REFRESHED, true))
            }
            else -> {
                workDataOf(Pair(TOKEN_REFRESHED, false))
            }
        }
    }


}