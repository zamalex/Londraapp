package com.gemidroid.londra.home.ui.department

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import kotlinx.android.synthetic.main.color_name_item.view.*
import kotlinx.android.synthetic.main.item_additional.view.*

class AdditionalAdapter(
    private val names: List<ProductDetailsRes.Data.Additional.Value>, val onItemClick:(ProductDetailsRes.Data.Additional.Value)->Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_additional, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemColor = names[position]

        holder.itemView.apply {
           rd3.isChecked = itemColor.selected
        }
        holder.itemView.apply {
            rd3.text = itemColor.label
            rd3.setOnClickListener {

                    names.forEach {
                        it.selected = false
                    }
                    itemColor.selected = true
                    onItemClick.invoke(itemColor)

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