package com.gemidroid.londra.home.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.main.model.CatRes
import kotlinx.android.synthetic.main.order_item.view.*

class DepartmentsAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var catsList = ArrayList<CatRes.Data>()

    fun setCats(list:ArrayList<CatRes.Data>){
        catsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            txt_order_name.text = catsList[position].name
            setOnClickListener {
                onItemClick.invoke(catsList[position].id)
            }

        }
    }

    override fun getItemCount(): Int {
        return catsList.size
    }

}
