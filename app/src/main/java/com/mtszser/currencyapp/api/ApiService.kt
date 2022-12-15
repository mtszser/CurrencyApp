package com.mtszser.currencyapp.api

import com.mtszser.currencyapp.model.CurrencyLatestData
import com.mtszser.currencyapp.util.Const
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate

interface ApiService {

    @GET("fixer/latest")
    fun getAllLatestCurrencies(
        @Query("base") base: String,
        @Query("apikey") apikey: String,
    ): Call<CurrencyLatestData>


    @GET("fixer/{date}")
    suspend fun getDateCurrencies(
        @Path("date") date: String,
        @Header("apikey") apikey: String = Const.API_KEY,
    ): Response<CurrencyLatestData>

}