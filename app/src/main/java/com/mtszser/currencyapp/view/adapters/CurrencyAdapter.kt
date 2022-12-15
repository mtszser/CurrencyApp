package com.mtszser.currencyapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtszser.currencyapp.R
import com.mtszser.currencyapp.databinding.CurrencyItemRvBinding
import com.mtszser.currencyapp.model.CurrencyItemsView

class CurrencyAdapter(): ListAdapter<CurrencyItemsView, CurrencyAdapter.ViewCurrencyHolder>(ListDiffCallBack) {

    inner class ViewCurrencyHolder(view: View): RecyclerView.ViewHolder(view) {

        private var binding = CurrencyItemRvBinding.bind(view)

        val currencySymbolAndRate = binding.currencySymbolAndRate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCurrencyHolder =
        ViewCurrencyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item_rv, parent, false)
    )



    override fun onBindViewHolder(holder: ViewCurrencyHolder, position: Int) {
        val currencyItem = getItem(position)
        holder.currencySymbolAndRate.text = currencyItem.currencyRates.toString()
    }
}

object ListDiffCallBack: DiffUtil.ItemCallback<CurrencyItemsView>() {
    override fun areItemsTheSame(oldItem: CurrencyItemsView, newItem: CurrencyItemsView): Boolean
            = oldItem.currencyRates == newItem.currencyRates

    override fun areContentsTheSame(oldItem: CurrencyItemsView, newItem: CurrencyItemsView): Boolean
            = oldItem == newItem
}