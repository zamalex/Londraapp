package com.gemidroid.londra.home.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import kotlinx.android.synthetic.main.order_summary_item.view.*

class OrderSummaryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var orders = ArrayList<AddProductRes.Data.Item>()

    fun setList(orders: ArrayList<AddProductRes.Data.Item>){
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_summary_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = orders[position]
        holder.itemView.apply {
         txt_piece_piece.text= StringBuffer().append(" ${item.name} ").append("  X${item.quantity} ")
         txt_piece_price.text="${item.sellingPrice} ريال"
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

}
