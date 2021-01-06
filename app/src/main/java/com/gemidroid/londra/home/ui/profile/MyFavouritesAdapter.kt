package com.gemidroid.londra.home.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourites_item.view.*

class MyFavouritesAdapter(val onFav:(Int)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mlst = ArrayList<CatProducstRes.Data.Data>()
    fun setList(array:ArrayList<CatProducstRes.Data.Data>){
        mlst = array
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourites_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mlst[position]

        holder.itemView.apply {
            txt_my_favourites_piece.text = item.name
            txt_my_favourites_brand.text = item.name
//            txt_my_favourites_color.text = item.colors.name
            txt_my_favourites_price.text = StringBuffer().append(item.price).append(" ريال ")

            if (item.in_favourite==1){
                add_to_fav.setImageResource(R.drawable.ic_favorite)

            }else
                add_to_fav.setImageResource(R.drawable.ic_un_favorite)

            if (!item.thumbnail.isNullOrEmpty()){
                Picasso.get().load(item.thumbnail).into(img_my_favourites)
            }

            add_to_fav.setOnClickListener { onFav.invoke(item.id)
                mlst.removeAt(position)
                notifyDataSetChanged()
            }

        }

    }

    override fun getItemCount(): Int {
        return mlst.size
    }
}