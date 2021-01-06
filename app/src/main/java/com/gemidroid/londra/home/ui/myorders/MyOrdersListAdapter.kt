package com.gemidroid.londra.home.ui.myorders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.my_order_item.view.*

class MyOrdersListAdapter(
    val onQuantityChange: (item: AddProductRes.Data.Item) -> Unit,
    val onDeleteClick: (item: AddProductRes.Data.Item) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<AddProductRes.Data.Item> = ArrayList()

    fun setData(list: List<AddProductRes.Data.Item>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_order_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: AddProductRes.Data.Item = list[position]

        holder.itemView.let {
            it.txt_my_order_name.text = item.name
            it.txt_my_order_color.text = "اللون:" + item.selectedColor
            it.qty_txt.text = item.quantity.toString()
            it.txt_my_order_price.text = StringBuffer().append(item.sellingPrice).append(" ريال ")

            if (item.userSize.id == 0)
                it.txt_my_order_sizes.text = "المقاس:" + item.selectedSize
            else
                it.txt_my_order_sizes.text = " المقاسات: محيط الصدر${item.userSize.chest} سم-الوسط${item.userSize.waist} سم-طول الذراع${item.userSize.armLength} سم-عرض الذراع${item.userSize.armWidth}سم-الطول ${item.userSize.height}سم"

            if (!item.thumbnail.isNullOrEmpty())
                Picasso.get().load(item.thumbnail).into(it.img_my_order)

            it.btn_increase.setOnClickListener {
                item.quantity++
                onQuantityChange.invoke(item)
                notifyDataSetChanged()
            }
            it.btn_decrease.setOnClickListener {
                if (item.quantity == 1)
                    return@setOnClickListener
                item.quantity--
                onQuantityChange.invoke(item)
                notifyDataSetChanged()

            }
            it.img_delete.setOnClickListener {
                onDeleteClick.invoke(item)
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
