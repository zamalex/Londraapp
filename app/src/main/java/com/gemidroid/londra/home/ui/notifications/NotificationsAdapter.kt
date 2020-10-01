package com.gemidroid.londra.home.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.data.notifications.Notifications

class NotificationsAdapter(
    private val notificationList: List<Notifications>,
    private val onItemClick: (Notifications) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(
                R.layout.notification_item,
                parent, false
            )
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


       /* val notificationItem = notificationList[position]

        holder.itemView.apply {
            setOnClickListener {
                onItemClick.invoke(notificationItem)
            }
        }*/
    }

    override fun getItemCount(): Int {
        return 5
    }
}