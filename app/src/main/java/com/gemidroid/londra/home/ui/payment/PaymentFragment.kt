package com.gemidroid.londra.home.ui.payment;

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
import com.gemidroid.londra.home.ui.department.DepartmentActivity
import com.gemidroid.londra.home.ui.profile.ProfileViewModel
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_payment.*
import retrofit2.HttpException
import java.net.UnknownHostException

class PaymentFragment : Fragment() {

    val loginRes = Paper.book().read<LoginRes>("login", LoginRes())
    val profileViewModel by activityViewModels<ProfileViewModel>()
    val viewModel by activityViewModels<PaymentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as PaymentActivity).loading!!.show()
        profileViewModel.getAddresses("Bearer ${loginRes.data.accessToken}")


        btn_activate.setOnClickListener {
            if (edt_code.text.isNullOrEmpty()) {
                edt_code.error = "required"
                return@setOnClickListener
            }
            (activity as PaymentActivity).loading!!.show()
            viewModel.checkCoupon("Bearer ${loginRes.data.accessToken}", JsonObject().apply { addProperty("coupon",edt_code.text.toString()) })
        }

        profileViewModel.getAddressResponse.observe(viewLifecycleOwner, Observer {
            (activity as PaymentActivity).loading!!.dismiss()

            if (it != null && it.success) {
                grp_addresses.apply {
                    adapter =
                        CartAddressAdapter().also { i -> i.setList(it.data as ArrayList<AddAddressResponse.Data>) }
                }
            }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            (activity as PaymentActivity).loading!!.dismiss()

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


        img_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        rec_order_summary.apply {
            adapter = OrderSummaryAdapter()
        }
    }
}
