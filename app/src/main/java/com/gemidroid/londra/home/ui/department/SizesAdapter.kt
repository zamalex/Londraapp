package com.gemidroid.londra.home.ui.department

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import kotlinx.android.synthetic.main.color_name_item.view.*

class SizesAdapter(
    private val names: List<ProductDetailsRes.Data.Size.Value>, val onItemClick:(ProductDetailsRes.Data.Size.Value)->Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_name_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemColor = names[position]

        holder.itemView.txt_color_name.apply {
            if (!itemColor.selected) {
                background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.edt_shape_grey)
                setTextColor(context.resources.getColor(R.color.colorGreyBlack))
            } else {
                txt_color_name.setTextColor(context.resources.getColor(R.color.colorBlack))
                holder.itemView.txt_color_name.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.edt_shape_black)
            }
        }
        holder.itemView.apply {
            txt_color_name.text = itemColor.label
            setOnClickListener {
                if (!itemColor.selected) {
                    names.forEach {
                        it.selected = false
                    }
                    itemColor.selected = true
                    onItemClick.invoke(itemColor)
                } else {
                    itemColor.selected = false
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }

    data class ColorViewItem(
        var name: String,
        var isSelected: Boolean = false,
    )

}