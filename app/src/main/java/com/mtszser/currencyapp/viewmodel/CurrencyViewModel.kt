package com.mtszser.currencyapp.viewmodel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtszser.currencyapp.model.*
import com.mtszser.currencyapp.repository.CurrencyRepository
import com.mtszser.currencyapp.view.SelectedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val currencyRepository: CurrencyRepository): ViewModel() {


    private val _navigation = MutableSharedFlow<NavigationEvent>()
    val navigation = _navigation as SharedFlow<NavigationEvent>

    private val _currencyState = MutableLiveData(CurrencyStateData())
    val currencyState = _currencyState as LiveData<CurrencyStateData>


    init {
        loadCurrencies()

    }

    fun loadCurrencies() {
        viewModelScope.launch {
            when(val response = currencyRepository.getCurrenciesResponse(date = getPreviousDay(_currencyState.value?.days ?: 0))){
                is CurrencyRepository.NetworkEvent.Success -> {
                    val list = response.data?.mapToCurrencyItemList(
                        currentList = _currencyState.value?.currencies ?: listOf()
                    )

                    _currencyState.value = _currencyState.value?.copy(
                        currencies = list ?: listOf(),
                        days = _currencyState.value?.days?.plus(1) ?: 0
                    )
                }
                is CurrencyRepository.NetworkEvent.Error -> {}
            }
        }
    }


    private fun getPreviousDay(previousDay: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().minusDays(previousDay).format(formatter)
    }

    fun getItemDetails(currencyItem: CurrencyItem){
        viewModelScope.launch {
            _navigation.emit(NavigationEvent.ItemDetails)
            _currencyState.value = _currencyState.value?.copy(
                currencyItem = currencyItem
            )
        }
    }

}

data class CurrencyStateData(
    val currencies: List<CurrencyItem> = listOf(),
    val days: Long = 0,
    val currencyItem: CurrencyItem? = null,
)

sealed class NavigationEvent {
object ItemDetails: NavigationEvent()
}
