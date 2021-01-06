package com.gemidroid.londra.home.ui.profile

import android.location.Address
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import kotlinx.android.synthetic.main.address_item.view.*

class MyAddressesAdapter(
    private val onEditClick: (Int) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var addressesList = ArrayList<AddAddressResponse.Data>()

    fun setList(lis:ArrayList<AddAddressResponse.Data>){
        addressesList = lis
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            img_address_dot.text = (position+1).toString()
            txt_notification_date.text = addressesList[position].address
            txt_address_title.text = addressesList[position].title
            img_address_edit.setOnClickListener {
                onEditClick.invoke(addressesList[position].id)
            }
        }
    }

    override fun getItemCount(): Int {
        return addressesList.size
    }
}