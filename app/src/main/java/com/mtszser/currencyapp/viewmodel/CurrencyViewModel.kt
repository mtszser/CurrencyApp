package com.mtszser.currencyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtszser.currencyapp.model.*
import com.mtszser.currencyapp.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val currencyRepository: CurrencyRepository): ViewModel() {

    private val _currencyState = MutableLiveData(CurrencyStateData())
    val currencyState = _currencyState as LiveData<CurrencyStateData>


    init {
        loadCurrencies()

    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            val currencyList= currencyRepository.getCurrenciesResponse(date = getDate()).mapToCurrencyItemList()
            val map: MutableMap<String, Double> = HashMap(currencyList.currencyRates)
            val listOfItems = map.entries.map { CurrencyModel.CurrencyItems(symbol = it.key, rate = it.value) }
            listOfItems.forEach {
                _currencyState.value = _currencyState.value?.copy(
                currModel = listOf(CurrencyModel.CurrencyDayHeader(date = currencyList.dateOfExchangeRate), CurrencyModel.CurrencyItems(symbol = it.symbol, rate = it.rate))
            ) }



            _currencyState.value = _currencyState.value?.copy(
                currencyMapList = currencyList.currencyRates,
                currencyListItem = map.entries.map { CurrencyItem(currencySymbol = it.key, currencyRate = it.value) },
                dateOfExchange = currencyList.dateOfExchangeRate,
                currencyItems = map.entries.map { CurrencyModel.CurrencyItems(symbol = it.key, rate = it.value) })
        }
    }



    private fun getDate(): String {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().format(formatter)
    }

    private fun getPreviousDay(previousDay: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().minusDays(previousDay).format(formatter)
    }


}

data class CurrencyStateData(
    val dateOfExchange: String = "",
    val currencyItems: List<CurrencyModel.CurrencyItems> = listOf(),
    val currModel: List<CurrencyModel> = listOf(),
    val currencyListItem: List<CurrencyItem> = listOf(),
    val currencyMapList: Map<String, Double> = mapOf(),
)