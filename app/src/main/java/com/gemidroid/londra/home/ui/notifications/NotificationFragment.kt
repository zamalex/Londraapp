package com.gemidroid.londra.home.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.login.ui.model.LoginRes
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit2.HttpException
import java.net.UnknownHostException

class NotificationFragment : Fragment() {

    val notificationsViewModel:NotificationsViewModel by activityViewModels()
    val loginRes:LoginRes = Paper.book().read("login", LoginRes())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        notificationsViewModel.getNotifications("Bearer ${loginRes.data.accessToken}")

        notificationsViewModel.notificationsResponse.observe(viewLifecycleOwner, Observer {

            if (it!=null&&it.success){
                rec_notifications.apply {
                    adapter = NotificationsAdapter(it.data) {

                    }
                }
            }
        })
        notificationsViewModel.notificationsError.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.e("error", "setAddError: ", it)
                if (it is UnknownHostException)
                    Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
                else if (it is HttpException) {

                    Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT)
                        .show()
                } else
                    Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
            }
        })


        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }



    }
}