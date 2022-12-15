package com.mtszser.currencyapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtszser.currencyapp.model.CurrencyLatestData
import com.mtszser.currencyapp.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
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
            _currencyState.value = _currencyState.value?.copy(
                currencyResponse = currencyRepository.getCurrenciesResponse(getDate())
            )


        }
    }

    private fun getDate(): String {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        Log.d("date", "${LocalDateTime.now().format(formatter)}")
        return LocalDateTime.now().format(formatter)
    }


}

data class CurrencyStateData(

    val currencyResponse: CurrencyLatestData? = null,
    val currencyResponseErrorMessage: String = "",
    val listOfCurrencies: List<CurrencyLatestData> = listOf(),
)