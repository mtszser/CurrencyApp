package com.mtszser.currencyapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mtszser.currencyapp.R

import com.mtszser.currencyapp.model.CurrencyModel
//(private val onItemClicked: (CurrencyModel.CurrencyItems) -> Unit)
class CurrencyAdapter()
    :RecyclerView.Adapter< CurrencyAdapter.ViewCurrencyHolder>() {

    private var currencyList = mutableListOf<CurrencyModel>()

    companion object {
        const val VIEW_HEADER = 0
        const val VIEW_ITEM = 1
    }

    class ViewCurrencyHolder(view: View): RecyclerView.ViewHolder(view) {

        private fun bindHeader(currencyData: CurrencyModel.CurrencyDayHeader) {
            val currencyDate: TextView = itemView.findViewById(R.id.currencyDate)
            currencyDate.text = currencyData.date
        }

        private fun bindItem(currencyData: CurrencyModel.CurrencyItems) {
            val currencySymbol: TextView = itemView.findViewById(R.id.currencyExchangeRate)
            currencySymbol.text = "${currencyData.symbol} ${currencyData.rate}"

        }

        fun bindData(currencyData: CurrencyModel){
            when(currencyData) {
                is CurrencyModel.CurrencyDayHeader -> bindHeader(currencyData)
                is CurrencyModel.CurrencyItems -> bindItem(currencyData)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCurrencyHolder {
        val layout = when(viewType) {
            VIEW_HEADER -> R.layout.currency_item_rv
            VIEW_ITEM -> R.layout.currency_item_rv_child
            else -> 0
        }
        return ViewCurrencyHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return when(currencyList[position]){
            is CurrencyModel.CurrencyItems -> VIEW_ITEM
            is CurrencyModel.CurrencyDayHeader -> VIEW_HEADER
        }
    }

    override fun getItemCount(): Int = currencyList.size

    fun setCurrencyItems(currency: List<CurrencyModel>) {
        currencyList.addAll(currency)

    }


    override fun onBindViewHolder(holder: ViewCurrencyHolder, position: Int) {
        holder.bindData(currencyList[position])
    }
}

//object ListDiffCallBack: DiffUtil.ItemCallback<CurrencyItem>() {
//    override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean
//            = oldItem.currencySymbol == newItem.currencySymbol
//
//    override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean
//            = oldItem == newItem
//}