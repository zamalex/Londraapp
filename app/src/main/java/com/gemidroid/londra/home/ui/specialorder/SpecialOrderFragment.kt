package com.gemidroid.londra.home.ui.specialorder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.api.SuccessResponse
import com.gemidroid.londra.home.ui.department.DepartmentActivity
import com.gemidroid.londra.login.ui.LoginActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_design.*
import kotlinx.android.synthetic.main.special_order_fragment.*
import retrofit2.HttpException
import java.net.UnknownHostException

class SpecialOrderFragment : Fragment() {
    var  designerId:Int? = null
    val viewModel: SpecialOrderViewModel by activityViewModels()
    val loginRes = Paper.book().read("login", LoginRes())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.special_order_fragment,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!loginRes.success) {
            Toast.makeText(requireActivity(),"سجل الدخول اولا",Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finishAffinity()
            return
        }


        (activity as SpecialOrderActivity).loading?.show()
        viewModel.getDesigners()





        btn_send_request.setOnClickListener {
            if (validateInputs()) {
                (activity as SpecialOrderActivity).loading?.show()

                viewModel.requestSpecialOrder(
                    "Bearer ${loginRes.data.accessToken}",
                    JsonObject().apply {
                        addProperty("designer_id", designerId.toString())
                        addProperty("height", "${edt_length.text}")
                        addProperty("chest", "${edt_binch.text}")
                        addProperty("waist", "${edt_twist.text}")
                        addProperty("hips", "0")
                        addProperty("arm_length", "${edt_arm_length.text}")
                        addProperty("arm_width", "${edt_arm_width.text}")
                        addProperty("design_note", "${edt_design_idea.text}")
                    })
            }
        }

        viewModel.designersResponse.observe(viewLifecycleOwner, Observer {
            (activity as SpecialOrderActivity).loading?.dismiss()

            if (it!=null&&it.success){
                val popup = PopupMenu(requireActivity(), edt_choose_designer)
                it.data.forEachIndexed { index, data ->
                    popup.menu.add(0, data.id, index, data.name)//(groupId, itemId, order, title)

                }

                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {
                        edt_choose_designer.setText(p0!!.title)
                        designerId = p0.itemId

                        return false
                    }
                })
                edt_choose_designer.setOnClickListener {
                    popup.show()
                }
            }


        })
        viewModel.requestResponse.observe(viewLifecycleOwner, Observer {
            (activity as SpecialOrderActivity).loading?.show()

        })


        viewModel.error.observe(viewLifecycleOwner, Observer {
            (activity as SpecialOrderActivity).loading?.dismiss()


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


                        Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT)
                            .show()
                } else
                    Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
            }

        })

        img_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    fun validateInputs(): Boolean {
        if (designerId==null) {
            edt_choose_designer.error = "required"
            return false
        }
        if (edt_binch.text.isNullOrEmpty()) {
            edt_binch.error = "required"
            return false
        }
        if (edt_twist.text.isNullOrEmpty()) {
            edt_twist.error = "required"
            return false
        }
        if (edt_arm_length.text.isNullOrEmpty()) {
            edt_arm_length.error = "required"
            return false
        }
        if (edt_arm_width.text.isNullOrEmpty()) {
            edt_arm_width.error = "required"
            return false
        }
        if (edt_length.text.isNullOrEmpty()) {
            edt_length.error = "required"
            return false
        }
        if (edt_design_idea.text.isNullOrEmpty()) {
            edt_design_idea.error = "required"
            return false
        }
        return true
    }
}