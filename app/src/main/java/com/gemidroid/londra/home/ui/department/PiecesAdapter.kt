package com.gemidroid.londra.home.ui.department

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.piece_item.view.*

class PiecesAdapter(
    private val onPieceClick: (Int) -> Unit,
    private val onLikedClick: (ImageView) -> (Unit),
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.piece_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            img_add_to_fav.setOnClickListener {
                onLikedClick.invoke(it as ImageView)
            }
            setOnClickListener {
                onPieceClick.invoke(position)
            }
        }


    }

    override fun getItemCount(): Int {
        return 9
    }

}
