package com.mtszser.currencyapp.di

import android.app.Application
import com.mtszser.currencyapp.api.ApiClient
import com.mtszser.currencyapp.api.ApiService
import com.mtszser.currencyapp.util.Const
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideBaseUrl() = Const.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().
        connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .client(provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()

    @Singleton
    @Provides
    fun provideApiService(): ApiService = provideRetrofit().create(ApiService::class.java)








}