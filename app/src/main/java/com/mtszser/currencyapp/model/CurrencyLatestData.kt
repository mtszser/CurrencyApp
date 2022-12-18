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

fun CurrencyLatestData.mapToCurrencyItemList(currentList: List<CurrencyItem>): List<CurrencyItem> =
    currentList + listOf(CurrencyItem(label = date, type = Types.Header)) + rates.flatMap { (key, value) ->
        listOf(CurrencyItem(label = key, value = value.toString(), type = Types.Currency))
    }

