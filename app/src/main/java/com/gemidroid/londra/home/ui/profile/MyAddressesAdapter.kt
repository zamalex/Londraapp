package com.gemidroid.londra.home.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.address_item.view.*

class MyAddressesAdapter(
    private val onEditClick: (Int) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            img_address_edit.setOnClickListener {
                onEditClick.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}