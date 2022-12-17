package com.mtszser.currencyapp.model

sealed class CurrencyModel {
    data class CurrencyDayHeader(
        val date: String
    ): CurrencyModel()

    data class CurrencyItems(
        val symbol: String,
        val rate: Double,
    ): CurrencyModel()
}
