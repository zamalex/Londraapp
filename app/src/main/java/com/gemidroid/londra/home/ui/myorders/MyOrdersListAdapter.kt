package com.gemidroid.londra.home.ui.myorders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import kotlinx.android.synthetic.main.my_order_item.view.*

class MyOrdersListAdapter(val onIncrease: (item:AddProductRes.Data.Item) -> Unit,val onDecrease: (item:AddProductRes.Data.Item) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list:List<AddProductRes.Data.Item> = ArrayList()

    fun setData(list:List<AddProductRes.Data.Item>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_order_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item:AddProductRes.Data.Item = list[position]

        holder.itemView.let {
            it.txt_my_order_name.text = item.name
            it.txt_my_order_color.text = "اللون:"+item.selectedColor
            it.qty_txt.text =item.quantity.toString()
            it.txt_my_order_price.text= StringBuffer().append(item.sellingPrice).append(" ريال ")

            it.btn_increase.setOnClickListener {
                item.quantity++
                onIncrease.invoke(item)
                notifyDataSetChanged()
            }
            it.btn_decrease.setOnClickListener {
                if (item.quantity==1)
                    return@setOnClickListener
                item.quantity--
                onDecrease.invoke(item)
                notifyDataSetChanged()

            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
