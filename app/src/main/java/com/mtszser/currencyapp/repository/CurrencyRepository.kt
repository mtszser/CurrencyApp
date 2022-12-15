package com.mtszser.currencyapp.repository

import com.mtszser.currencyapp.api.ApiService
import com.mtszser.currencyapp.model.CurrencyLatestData
import javax.inject.Inject


class CurrencyRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCurrenciesResponse(date: String): CurrencyLatestData {
        val response = apiService.getDateCurrencies(date = date)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return response.body()!!
    }
}