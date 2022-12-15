package com.mtszser.currencyapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import com.mtszser.currencyapp.api.ApiClient
import com.mtszser.currencyapp.databinding.ActivityMainBinding
import com.mtszser.currencyapp.model.CurrencyLatestData
import com.mtszser.currencyapp.util.Const
import com.mtszser.currencyapp.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val currencyViewModel: CurrencyViewModel by viewModels()
    private lateinit var apiClient: ApiClient

    private val textView
    get() = binding.textView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadCurrencyData()

    }



    private fun loadCurrencyData() {
        currencyViewModel.currencyState.observe(this) { currencyState ->
            val response = currencyState.currencyResponse
            Log.d("response", "$response")
        }

        }
    }