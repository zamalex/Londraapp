package com.gemidroid.londra.home.ui.myorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import kotlinx.android.synthetic.main.fragment_my_orders_list.*
import kotlinx.android.synthetic.main.fragment_my_orders_list.rec_orders_list
import kotlinx.android.synthetic.main.fragment_my_prev_orders_list.*

class MyPrevOrdersListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_prev_orders_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rec_prev_orders_list.apply {

            adapter= MyPrevOrdersListAdapter()
        }
    }
}
