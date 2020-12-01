package com.gemidroid.londra.home.ui.department

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import kotlinx.android.synthetic.main.client_rating_item.view.*

class ClientRatingAdapter(val list:ArrayList<ProductDetailsRes.Data.Review>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.client_rating_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val revirw:ProductDetailsRes.Data.Review = list[position]

        holder.itemView.let { i->
            i.rating.rating = revirw.rating.toFloat()
            i.txt_rate_content.text = revirw.comment
            i.txt_rate_date.text = revirw.createdAt
            i.txt_rate_author.text = revirw.reviewerName
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
