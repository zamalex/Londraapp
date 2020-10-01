package com.gemidroid.londra.home.ui.myorders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.payment.PaymentActivity
import kotlinx.android.synthetic.main.fragment_my_orders_list.*

class MyOrdersListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_orders_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rec_orders_list.apply {
            adapter = MyOrdersListAdapter(){
            }
        }

        btn_payment.setOnClickListener {
            startActivity(Intent(requireActivity(), PaymentActivity::class.java))
        }
    }
}
