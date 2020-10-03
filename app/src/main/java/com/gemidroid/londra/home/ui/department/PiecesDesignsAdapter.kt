package com.gemidroid.londra.home.ui.department

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.pieces_design_item.view.*

class PiecesDesignsAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pieces_design_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.img_piece_design.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

}
