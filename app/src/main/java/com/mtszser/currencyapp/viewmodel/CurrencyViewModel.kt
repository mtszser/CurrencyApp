package com.mtszser.currencyapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtszser.currencyapp.model.CurrencyItemsView
import com.mtszser.currencyapp.model.CurrencyLatestData
import com.mtszser.currencyapp.model.mapToCurrencyItemList
import com.mtszser.currencyapp.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val currencyRepository: CurrencyRepository): ViewModel() {

    private val _currencyState = MutableLiveData(CurrencyStateData())
    val currencyState = _currencyState as LiveData<CurrencyStateData>


    init {
        loadCurrencies()

    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            val currentDay = currencyRepository.getCurrenciesResponse(date = getDate()).mapToCurrencyItemList()
            _currencyState.value = _currencyState.value?.copy(
                currencyItem = currencyRepository.getCurrenciesResponse(getDate()).mapToCurrencyItemList(),
                currencyList = listOf(currencyRepository.getCurrenciesResponse(getDate()).mapToCurrencyItemList()),
                date = currentDay.dateOfExchangeRate

            )


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

    val currencyList: List<CurrencyItemsView> = listOf(),
    val currencyItem: CurrencyItemsView? = null,
    val currencyResponseErrorMessage: String = "",
    val date: String = "",
    val listOfCurrencies: List<CurrencyLatestData> = listOf(),
)