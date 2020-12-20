package com.gemidroid.londra.home.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.profile.ProfileViewModel
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.update_address_fragment.*

class UpdateAddressFragment : Fragment() {

    var addressId: Int = 0
    private val loginRes by lazy {
        Paper.book().read("login", LoginRes())
    }
    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_address_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressId = requireActivity().intent.getIntExtra("addressId", 0)

        btn_edit_address.setOnClickListener {


            (activity as UpdateAddressActivity).loading!!.show()
            if (addressId == 0) {
                viewModel.addAddresses(
                    "Bearer ${loginRes.data.accessToken}",
                    JsonObject().apply {
                        addProperty(
                            "address",
                            edt_address_details.text.toString()
                        )
                        addProperty("type", "house")
                    })
            } else {
                viewModel.updateAddress(
                    addressId.toString(),
                    "Bearer ${loginRes.data.accessToken}",
                    JsonObject().apply {
                        addProperty(
                            "address",
                            edt_address_details.text.toString()
                        )
                        addProperty("type", "house")

                    })
            }
        }


        viewModel.getAddressResponse.observe(viewLifecycleOwner, Observer {
            (activity as UpdateAddressActivity).loading!!.dismiss()
            requireActivity().finish()

        })
        viewModel.getAddressError.observe(
            viewLifecycleOwner,
            Observer { (activity as UpdateAddressActivity).loading!!.dismiss() })
    }


}