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
import com.gemidroid.londra.api.SuccessResponse
import com.gemidroid.londra.home.ui.department.DepartmentActivity
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import com.gemidroid.londra.home.ui.profile.ProfileViewModel
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_my_orders_list.*
import kotlinx.android.synthetic.main.fragment_payment.*
import retrofit2.HttpException
import java.net.UnknownHostException

class PaymentFragment : Fragment() {
    var orders: AddProductRes? = null
    var selectedAddress: Int? = null
    val cart: String? = Paper.book().read("cart", null)
    var jsonObject = JsonObject()
    val loginRes = Paper.book().read<LoginRes>("login", LoginRes())
    val profileViewModel by activityViewModels<ProfileViewModel>()
    val viewModel by activityViewModels<PaymentViewModel>()
    var total = 0f
    var coupon = 0f
    var shipping = 0f
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orders = Gson().fromJson(
            requireActivity().intent.getStringExtra("orders"),
            AddProductRes::class.java
        )
        //Toast.makeText(activity,orders!!.data.total_price.toString(),Toast.LENGTH_SHORT).show()

        if (orders != null) {
            total = orders!!.data.total_price
            shipping = orders!!.data.shipping_price

            txt_total_orders.text = orders!!.data.sellingPrice.toString() + "ريال"
            txt_total_charge.text = orders!!.data.shipping_price.toString() + "ريال"
            txt_total_pricess.text = orders!!.data.total_price.toString() + "ريال"
            rec_order_summary.apply {
                adapter =
                    OrderSummaryAdapter().also { i -> i.setList(orders!!.data.items as ArrayList<AddProductRes.Data.Item>) }
            }
        }
        (activity as PaymentActivity).loading!!.show()
        profileViewModel.getAddresses("Bearer ${loginRes.data.accessToken}")



        btn_pay_now.setOnClickListener {
            if (selectedAddress == null) {
                Toast.makeText(activity, "select address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            jsonObject.addProperty("address_id", selectedAddress)
            jsonObject.addProperty("cart_id", cart)
            if (!edt_code.text.isNullOrEmpty())
                jsonObject.addProperty("coupon", edt_code.text.toString())
            if (!edt_notes.text.isNullOrEmpty())
                jsonObject.addProperty("shipping_notes", edt_notes.text.toString())
            (activity as PaymentActivity).loading!!.show()

            viewModel.createOrder("Bearer ${loginRes.data.accessToken}", jsonObject)


        }


        btn_activate.setOnClickListener {
            if (edt_code.text.isNullOrEmpty()) {
                edt_code.error = "required"
                return@setOnClickListener
            }
            (activity as PaymentActivity).loading!!.show()
            viewModel.checkCoupon(
                "Bearer ${loginRes.data.accessToken}",
                JsonObject().apply { addProperty("coupon", edt_code.text.toString()) })
        }

        viewModel.createOrderResponse.observe(viewLifecycleOwner, Observer {
            (activity as PaymentActivity).loading!!.dismiss()

        })

        viewModel.couponResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && it.success) {
                coupon = it.data.discount

                txt_total_orders.text = (orders!!.data.sellingPrice-(orders!!.data.sellingPrice*(coupon/100))).toString() + "ريال"
                txt_total_charge.text = (shipping-(shipping*coupon/100)).toString() + "ريال"
                txt_total_pricess.text = (total-(total*coupon/100)).toString() + "ريال"
            }
        })

        profileViewModel.getAddressResponse.observe(viewLifecycleOwner, Observer {
            (activity as PaymentActivity).loading!!.dismiss()

            if (it != null && it.success) {
                grp_addresses.apply {
                    adapter =
                        CartAddressAdapter({ addressId ->
                            selectedAddress = addressId

                        }).also { i -> i.setList(it.data as ArrayList<AddAddressResponse.Data>) }
                }
            }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            (activity as PaymentActivity).loading!!.dismiss()

            if (it != null) {
                if (it is UnknownHostException)
                    Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
                else if (it is HttpException) {
                    // Log.e("eeee", it.response()!!.errorBody()!!.string())
                    var successResponse = Gson().fromJson(
                        it.response()!!.errorBody()!!.string(),
                        SuccessResponse::class.java
                    )
                    if (successResponse != null && !successResponse.message.isNullOrEmpty())
                        Toast.makeText(activity, successResponse.message, Toast.LENGTH_SHORT)
                            .show()
                    else


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
        viewModel.createOrderError.observe(viewLifecycleOwner, Observer {
            (activity as PaymentActivity).loading!!.dismiss()

            if (it != null) {
                if (it is UnknownHostException)
                    Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
                else if (it is HttpException) {
                    // Log.e("eeee", it.response()!!.errorBody()!!.string())
                    var successResponse = Gson().fromJson(
                        it.response()!!.errorBody()!!.string(),
                        SuccessResponse::class.java
                    )
                    if (successResponse != null && !successResponse.message.isNullOrEmpty())
                        Toast.makeText(activity, successResponse.message, Toast.LENGTH_SHORT)
                            .show()
                    else


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
        profileViewModel.getAddressError.observe(viewLifecycleOwner, Observer {
            (activity as PaymentActivity).loading!!.dismiss()

            if (it != null) {
                if (it is UnknownHostException)
                    Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
                else if (it is HttpException) {
                    // Log.e("eeee", it.response()!!.errorBody()!!.string())
                    var successResponse = Gson().fromJson(
                        it.response()!!.errorBody()!!.string(),
                        SuccessResponse::class.java
                    )
                    if (successResponse != null && !successResponse.message.isNullOrEmpty())
                        Toast.makeText(activity, successResponse.message, Toast.LENGTH_SHORT)
                            .show()
                    else


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


    }
}
