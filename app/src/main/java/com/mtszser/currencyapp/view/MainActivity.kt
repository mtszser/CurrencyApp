package com.mtszser.currencyapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtszser.currencyapp.databinding.ActivityMainBinding
import com.mtszser.currencyapp.view.adapters.CurrencyAdapter
import com.mtszser.currencyapp.viewmodel.CurrencyViewModel
import com.mtszser.currencyapp.viewmodel.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val currencyViewModel: CurrencyViewModel by viewModels()
    private lateinit var currencyAdapter: CurrencyAdapter


    private val currencyRecyclerView
        get() = binding.currencyRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadCurrencyData()

    }


    private fun loadCurrencyData() {
        currencyAdapter = CurrencyAdapter(
            onItemClicked = currencyViewModel::getItemDetails
        )
        currencyRecyclerView.layoutManager = LinearLayoutManager(this)
        currencyRecyclerView.adapter = currencyAdapter

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                currencyViewModel.navigation.collect { event ->
                    when(event){
                        NavigationEvent.ItemDetails -> {}
                    }
                }
            }
        }

        currencyViewModel.currencyState.observe(this) { currencyState ->
            currencyAdapter.submitList(currencyState.currencies)
            Log.d("dupa", "${currencyState.currencyItem}")

        }

        currencyRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if(linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == currencyAdapter.itemCount-1) {
                    currencyViewModel.loadCurrencies()

                }
            }
        })
    }

}