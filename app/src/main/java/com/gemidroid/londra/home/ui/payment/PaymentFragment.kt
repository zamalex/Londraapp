package com.gemidroid.londra.home.ui.payment;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.profile.ProfileViewModel
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import com.gemidroid.londra.login.ui.model.LoginRes
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_payment.*

class PaymentFragment : Fragment() {

    val loginRes = Paper.book().read<LoginRes>("login", LoginRes())
    val profileViewModel by activityViewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getAddresses("Bearer ${loginRes.data.accessToken}")


        profileViewModel.getAddressResponse.observe(viewLifecycleOwner, Observer {
            if (it!=null&&it.success){
                grp_addresses.apply {
                    adapter = CartAddressAdapter().also { i-> i.setList(it.data as ArrayList<AddAddressResponse.Data>) }
                }
            }
        })

        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        rec_order_summary.apply {
            adapter = OrderSummaryAdapter()
        }
    }
}
