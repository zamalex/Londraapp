package com.gemidroid.londra.home.ui.department

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.color_name_item.view.*

class ColorNamesAdapter(
    private val names: List<ColorViewItem>,
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
            if (!itemColor.isSelected) {
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
            txt_color_name.text = itemColor.name
            setOnClickListener {
                if (!itemColor.isSelected) {
                    names.forEach {
                        it.isSelected = false
                    }
                    itemColor.isSelected = true
                } else {
                    itemColor.isSelected = false
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
