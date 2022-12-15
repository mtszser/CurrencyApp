package com.mtszser.currencyapp.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class CurrencyLatestData(
    @field:Json(name = "base") val base: String,
    @field:Json(name = "date") val date: String,
    @field:Json(name = "rates") val rates: Map<String, Double>,
    @field:Json(name = "success") val success: Boolean
)

fun CurrencyLatestData.mapToCurrencyItemList() = CurrencyItemsView(
    baseCurrency = this.base,
    dateOfExchangeRate = this.date,
    currencyRates = this.rates,
    apiSuccess = this.success

)