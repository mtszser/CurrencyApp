package com.mtszser.currencyapp.model

import java.io.Serializable
import java.util.*

data class CurrencyItem(
    val id: String = UUID.randomUUID().toString(),
    val label: String,
    val value: String? = null,
    val type: Types,
): Serializable

enum class Types {
    Header,
    Currency,
}

