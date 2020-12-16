package com.gemidroid.londra.home.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.londra.R
import com.gemidroid.londra.home.data.notifications.Notifications
import kotlinx.android.synthetic.main.notification_item.view.*

class NotificationsAdapter(
    private val notificationList: List<NotificationRes.Data>,
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


        val notificationItem = notificationList[position]

        holder.itemView.apply {
            txt_notification_title.text=notificationItem.content
            txt_notification_date.text = notificationItem.createdAtHuman

        }
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}