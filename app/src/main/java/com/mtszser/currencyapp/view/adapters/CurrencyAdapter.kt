package com.mtszser.currencyapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtszser.currencyapp.R
import com.mtszser.currencyapp.databinding.CurrencyItemRvBinding
import com.mtszser.currencyapp.model.CurrencyItem

import com.mtszser.currencyapp.model.CurrencyModel
import com.mtszser.currencyapp.model.Types
class CurrencyAdapter(private val onItemClicked: (CurrencyItem) -> Unit) : ListAdapter<CurrencyItem, CurrencyAdapter.ViewItemHolder>(ListDiffCallBack) {

    inner class ViewItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding = CurrencyItemRvBinding.bind(view)


        fun bind(currencyItem: CurrencyItem) {
            val date = binding.currencyDate
            val exchangeRate = binding.currencyExchangeRate

            date.text = currencyItem.label
            exchangeRate.text = currencyItem.value

        }

    }

    override fun getItemViewType(position: Int): Int = when(currentList[position].type) {
        Types.Header -> 1
        Types.Currency -> 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemHolder = when(viewType) {
        1 -> {
            ViewItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.currency_item_rv, parent, false))
        }
        else -> {
            ViewItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.currency_item_rv, parent, false))
        }
    }

    override fun getItemCount(): Int  = currentList.size



    override fun onBindViewHolder(holder: ViewItemHolder, position: Int) {
        holder.bind(currentList[position])
        }
}

object ListDiffCallBack: DiffUtil.ItemCallback<CurrencyItem>() {
    override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean
            = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean
            = oldItem == newItem
}