package com.mtszser.currencyapp.model

data class CurrencyItemsView(
    val baseCurrency: String,
    val dateOfExchangeRate: String,
    val currencyRates: Map<String, Double>,
    val apiSuccess: Boolean

)
