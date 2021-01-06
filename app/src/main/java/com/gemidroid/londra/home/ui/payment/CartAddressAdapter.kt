package com.gemidroid.londra.home.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import kotlinx.android.synthetic.main.item_cart_address.view.*

class CartAddressAdapter(val selectAddress:(id:Int)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList = ArrayList<AddAddressResponse.Data>()

    fun setList(
        addresses: ArrayList<AddAddressResponse.Data>

    ) {
        mList = addresses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_address, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mList[position]

        holder.itemView.apply {
            rd_home.setText(StringBuffer().append(item.title).append("\n").append(item.address))
            rd_home.isChecked = item.isChecked
            rd_home.setOnClickListener {
                mList.forEach { i-> i.isChecked = false }
                mList[position].isChecked = true
                notifyDataSetChanged()
                selectAddress.invoke(item.id)
            }
        }
    }
}