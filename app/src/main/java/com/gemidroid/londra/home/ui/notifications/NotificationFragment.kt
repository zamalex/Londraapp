package com.gemidroid.londra.home.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        rec_notifications.apply {
            adapter = NotificationsAdapter(emptyList()) {

            }
        }

    }
}