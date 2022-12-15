package com.mtszser.currencyapp.api

import com.mtszser.currencyapp.util.Const
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private lateinit var apiService: ApiService

    private val moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()

    private val okhttp = OkHttpClient().newBuilder().
    connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .build()

    fun getApiClient(): ApiService {

        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(okhttp)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService


    }

}