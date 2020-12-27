package com.gemidroid.londra.home.ui.myorders

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
import com.gemidroid.londra.home.ui.specialorder.SpecialOrderActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_my_orders_list.*
import kotlinx.android.synthetic.main.fragment_my_orders_list.rec_orders_list
import kotlinx.android.synthetic.main.fragment_my_prev_orders_list.*
import retrofit2.HttpException
import java.net.UnknownHostException

class MyPrevOrdersListFragment : Fragment() {

    val loginRes= Paper.book().read<LoginRes>("login", LoginRes())
    val viewModel by activityViewModels<OrdersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_prev_orders_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getPrevOrders("Bearer ${loginRes.data.accessToken}")



        viewModel.prevResponse.observe(viewLifecycleOwner, Observer {
            if (it!=null&&it.success){
                rec_prev_orders_list.apply {

                    adapter= MyPrevOrdersListAdapter().also { i->i.setList(it.data as ArrayList<PrevOrdersRes.Data>) }
                }
            }
        })

        viewModel.cartError.observe(viewLifecycleOwner, Observer {


            if (it != null) {
                if (it is UnknownHostException)
                    Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
                else if (it is HttpException) {
                    Log.e("eeee", it.response()!!.errorBody()!!.string())

                    Toast.makeText(
                        activity,
                        it.response()!!.message().toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else
                    Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
