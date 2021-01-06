package com.gemidroid.londra.home.ui.myorders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.home.ui.department.DepartmentActivity
import com.gemidroid.londra.login.ui.LoginActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_my_orders.*
import retrofit2.HttpException
import java.net.UnknownHostException

class MyOrdersFragment : Fragment() {

    val loginRes = Paper.book().read("login", LoginRes())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!loginRes.success) {
            Toast.makeText(requireActivity(),"سجل الدخول اولا",Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finishAffinity()
            return
        }


        val sectionsPagerAdapter = OrdersPagerAdapter(
            requireContext(), childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

        pager_orders.adapter = sectionsPagerAdapter
        tab_my_orders.setupWithViewPager(pager_orders)


    }


}