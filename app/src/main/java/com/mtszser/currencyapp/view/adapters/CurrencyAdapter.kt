package com.mtszser.currencyapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtszser.currencyapp.R
import com.mtszser.currencyapp.databinding.CurrencyItemRvBinding
import com.mtszser.currencyapp.model.CurrencyItem
import com.mtszser.currencyapp.model.CurrencyItemsView

class CurrencyAdapter(): ListAdapter<CurrencyItem, CurrencyAdapter.ViewCurrencyHolder>(ListDiffCallBack) {


    inner class ViewCurrencyHolder(view: View): RecyclerView.ViewHolder(view) {

        private var binding = CurrencyItemRvBinding.bind(view)

        val currencySymbolAndRate = binding.currencySymbolAndRate
        val currencyDate = binding.currencyDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCurrencyHolder =
        ViewCurrencyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item_rv, parent, false)
    )




    override fun onBindViewHolder(holder: ViewCurrencyHolder, position: Int) {
        val currencyItem = getItem(position)
        holder.currencySymbolAndRate.text = currencyItem.currencyRate.toString()
        holder.currencyDate.text = currencyItem.currencySymbol
    }
}

object ListDiffCallBack: DiffUtil.ItemCallback<CurrencyItem>() {
    override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean
            = oldItem.currencySymbol == newItem.currencySymbol

    override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean
            = oldItem == newItem
}