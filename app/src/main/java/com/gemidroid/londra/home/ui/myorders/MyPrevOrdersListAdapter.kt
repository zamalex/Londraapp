package com.gemidroid.londra.home.ui.myorders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.my_prev_order_item.view.*

class MyPrevOrdersListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders = ArrayList<PrevOrdersRes.Data>()
    fun setList(orders: ArrayList<PrevOrdersRes.Data>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_prev_order_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = orders[position]
        holder.itemView.apply {
            txt_order_no.text = StringBuffer().append("طلب رقم ").append(item.id.toString())
            txt_order_date.text = item.createdAt
            txt_order_status.text = item.status
            txt_order_pieces.text = StringBuffer().append("عدد القطع: ").append(item.itemsNum.toString())
            txt_order_price.text = StringBuffer().append("إجمالي المبلغ: ").append(item.totalPrice.toString()).append(" ريال ")
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

}
