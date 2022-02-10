package com.primelab.nearBase.di

import android.content.Context
import com.primelab.nearBase.data.localcontact.ContactSource
import com.primelab.nearBase.data.localcontact.LocalContact
import com.primelab.nearBase.data.networks.Api
import com.primelab.nearBase.data.networks.ContactApi
import com.primelab.nearBase.data.networks.LoginApi
import com.primelab.nearBase.data.networks.UserApi
import com.primelab.nearBase.data.preference.SharePrefs
import com.primelab.nearBase.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        api: Api,
        contactApi: ContactApi,
        userApi: UserApi,
        loginApi: LoginApi,
        sharePrefs: SharePrefs,
        localContact: ContactSource
    ) =
        Repository(
            api,
            contactApi,
            userApi,
            loginApi,
            sharePrefs,
            localContact
        )

    @Provides
    @Singleton
    fun providerContactSource(@ApplicationContext context: Context): ContactSource =
        LocalContact(context)
}