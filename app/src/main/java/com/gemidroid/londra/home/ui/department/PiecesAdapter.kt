package com.gemidroid.londra.home.ui.department

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.piece_item.view.*

class PiecesAdapter(
    private val onPieceClick: (Int) -> Unit,
    private val onLikedClick: (ImageView,Int) -> (Unit),
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var prolist: ArrayList<CatProducstRes.Data.Data> = ArrayList()

    fun setList(mlist: ArrayList<CatProducstRes.Data.Data>) {
        prolist.addAll(mlist)
        notifyDataSetChanged()

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.piece_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {

            if (prolist[position].in_favourite==1){
                img_add_to_fav.setImageResource(R.drawable.ic_favorite)

            }else
                img_add_to_fav.setImageResource(R.drawable.ic_un_favorite)




            txt_piece_name.text = prolist[position].name
            txt_piece_price.text = "${prolist[position].price.toString()} ريال "

            img_add_to_fav.setOnClickListener {
                onLikedClick.invoke(it as ImageView,prolist[position].id)
            }
            setOnClickListener {
                onPieceClick.invoke(position)
            }
            if (!prolist[position].thumbnail.isNullOrEmpty())
                Picasso.get().load(prolist[position].thumbnail).placeholder(R.drawable.ic_mask).error(R.drawable.ic_mask).into(img_piece)
        }


    }

    override fun getItemCount(): Int {
        return prolist.size
    }



}
