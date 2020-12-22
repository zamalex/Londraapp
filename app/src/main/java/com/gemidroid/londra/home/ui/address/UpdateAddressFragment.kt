package com.gemidroid.londra.home.ui.address

import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.profile.ProfileViewModel
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.titanium.locgetter.main.LocationGetterBuilder
import io.paperdb.Paper
import kotlinx.android.synthetic.main.update_address_fragment.*
import java.util.*


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

        img_address_map.setOnClickListener { checkPermission() }

        viewModel.getAddressResponse.observe(viewLifecycleOwner, Observer {
            (activity as UpdateAddressActivity).loading!!.dismiss()
            requireActivity().finish()

        })
        viewModel.getAddressError.observe(
            viewLifecycleOwner,
            Observer { (activity as UpdateAddressActivity).loading!!.dismiss() })
    }
    fun checkPermission() {


        Dexter.withContext(activity)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        val locationGetter =
                            LocationGetterBuilder(requireActivity())
                                .build()
                        (activity as UpdateAddressActivity).loading!!.show()

                        locationGetter.getLatestLocation()
                            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribe({ location ->
                                (activity as UpdateAddressActivity).loading!!.dismiss()
                                edt_address_details.setText(getCompleteAddressString(location.latitude,location.longitude))

                            }, kotlin.Throwable::printStackTrace)

                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        // Toast.makeText(getActivity(), "قم بالسماح للتطبيق للوصول الى موقعك من خلال الاعدادات", Toast.LENGTH_LONG).show();
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }
    private fun getCompleteAddressString(
        LATITUDE: Double,
        LONGITUDE: Double
    ): String? {
        var strAdd = ""
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        try {
            val addresses: List<Address>? =
                geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.getMaxAddressLineIndex()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
            } else {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strAdd
    }
}