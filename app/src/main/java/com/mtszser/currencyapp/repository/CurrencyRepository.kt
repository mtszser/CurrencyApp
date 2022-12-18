package com.mtszser.currencyapp.repository

import com.mtszser.currencyapp.api.ApiService
import com.mtszser.currencyapp.model.CurrencyLatestData
import javax.inject.Inject


class CurrencyRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCurrenciesResponse(date: String): NetworkEvent<CurrencyLatestData> {
        val response = apiService.getDateCurrencies(date = date)
        return if (response.isSuccessful) {
            NetworkEvent.Success(data = response.body())
        } else {
            NetworkEvent.Error(message = response.message())
        }
    }

    sealed class NetworkEvent<out T : Any> {
        data class Success<out T : Any>(val data: T?) : NetworkEvent<T>()
        data class Error(val message: String) : NetworkEvent<Nothing>()
    }
}