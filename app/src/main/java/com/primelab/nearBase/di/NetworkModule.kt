package com.primelab.nearBase.di

import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.primelab.nearBase.data.networks.Api
import com.primelab.nearBase.data.networks.ContactApi
import com.primelab.nearBase.data.networks.LoginApi
import com.primelab.nearBase.data.networks.UserApi
import com.primelab.nearBase.data.networks.interceptor.TokenInterceptor
import com.primelab.nearBase.data.preference.SharePrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @BaseUrl
    @Singleton
    @Provides
    fun provideBaseUrl() = "https://eqxpg9y48j.execute-api.sa-east-1.amazonaws.com"


    @ContactUrl
    @Singleton
    @Provides
    fun provideContactUrl() = "https://eqxpg9y48j.execute-api.sa-east-1.amazonaws.com"


    @Singleton
    @Provides
    fun provideTokenInterceptor(sharedPreferences: SharePrefs): Interceptor {
        return TokenInterceptor(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideHttpClient(tokenInterceptor: Interceptor): OkHttpClient {
        val builder =  OkHttpClient.Builder()
            .addNetworkInterceptor(tokenInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
        // Use log level Header or Non. Log level Body can cause out of memory issues
        // while uploading large video or image
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideBaseService(
        @BaseUrl baseUrl: String,
        httpClient: OkHttpClient
    ): Api {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }




    @Provides
    @Singleton
    fun provideContactService(
        @ContactUrl url: String,
        httpClient: OkHttpClient
    ): ContactApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContactApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(
        @BaseUrl url: String,
        httpClient: OkHttpClient
    ): UserApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(
        @BaseUrl url: String,
        httpClient: OkHttpClient
    ): LoginApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}