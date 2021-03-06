package com.gemidroid.londra.home.ui.myorders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.api.SuccessResponse
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.home.ui.payment.PaymentActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.Gson
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_my_orders_list.*
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.*
import kotlin.concurrent.timerTask

class MyOrdersListFragment : Fragment() {
    val viewModel: OrdersViewModel by activityViewModels()
    val loginRes = Paper.book().read<LoginRes>("login", LoginRes())

    val cart: String? = Paper.book().read("cart", null)

    var timer = Timer()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_orders_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_payment.visibility = View.GONE
        if (cart != null) {
            (activity as HomeActivity).loading?.show()
            viewModel.listCart(cart)
        } else {
            (activity as HomeActivity).loading?.show()
            viewModel.getCartId("Bearer ${loginRes.data.accessToken}")
        }


        btn_payment.setOnClickListener {
            startActivity(
                Intent(requireActivity(), PaymentActivity::class.java).putExtra(
                    "orders",
                    Gson().toJson(viewModel.cartResponse.value)
                )
            )
        }

        setCartResponse()
        setCartError()
    }

    fun setCartResponse() = viewModel.cartResponse.observe(viewLifecycleOwner, Observer { r ->
        (activity as HomeActivity).loading?.dismiss()
        if (r != null && r.success) {
            if (r.data.items.isNullOrEmpty()) {
                btn_payment.visibility = View.GONE
            }else{
                btn_payment.visibility = View.VISIBLE
            }
            Paper.book().write("cart", r.data.cartId.toString())

            var total = 0
            r.data.items.forEach { i -> total += i.quantity }

            txt_total_pieces.text = "عدد القطع المختارة:(${total})"
            txt_total_prices.text = "الإجمالي: ${r.data.total_price} ريال"



            btn_payment.SetVisibility("الدفع", true)
            rec_orders_list.apply {
                adapter = MyOrdersListAdapter({ item ->
                    // btn_payment.isEnabled = false
                    btn_payment.SetVisibility("Loading...", false)

                    val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                        //  .addFormDataPart("user_id", loginRes.data.user.id.toString())
                        // .addFormDataPart("product_id", item.itemId.toString())
                        .addFormDataPart("quantity", "${item.quantity}")
                        .addFormDataPart("math_type", "balance")

                    if (cart != null)
                        builder.addFormDataPart("cart_id", cart!!)
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(timerTask {
                        viewModel.updateQuantity(item.itemId.toString(), builder.build())

                    }

                        , 1500)


                }, { deletedItem ->
                    (activity as HomeActivity).loading?.show()
                    viewModel.removeProduct(cart!!, deletedItem.itemId.toString())

                }).also { it.setData(r.data.items) }
            }

        }

    })

    fun setCartError() = viewModel.cartError.observe(viewLifecycleOwner, Observer {
        (activity as HomeActivity).loading?.dismiss()

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

    fun Button.SetVisibility(t: String, bb: Boolean) {
        isEnabled = bb
        text = t

    }
}
