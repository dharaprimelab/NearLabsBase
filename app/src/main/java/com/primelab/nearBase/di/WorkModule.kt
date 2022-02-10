package com.primelab.nearBase.di

import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.primelab.nearBase.work.RefreshWork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkModule {

    @Singleton
    @Provides
    fun providePeriodicWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<RefreshWork>(
            4, TimeUnit.HOURS, 15, TimeUnit.MINUTES
        ).addTag(RefreshWork.REFRESH_WORK).build()
    }
}