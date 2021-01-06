package com.gemidroid.londra.home.ui.design

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.londra.R
import com.gemidroid.londra.api.SuccessResponse
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.login.ui.LoginActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_design.*
import retrofit2.HttpException
import java.net.UnknownHostException


class DesignFragment : Fragment() {
    var hair: Int? = null
    var skin: Int? = null
    var body: Int? = null

    val viewModel: DesignViewModel by activityViewModels()
    val loginRes = Paper.book().read<LoginRes>("login", LoginRes())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_design, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!loginRes.success) {
            Toast.makeText(requireActivity(),"سجل الدخول اولا",Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finishAffinity()
            return
        }

        (activity as HomeActivity).loading!!.show()
        viewModel.getAttributes("Bearer ${loginRes.data.accessToken}", "")

        viewModel.hairResponse.observe(viewLifecycleOwner, Observer {

            if (it != null && it.success) {
                val popup = PopupMenu(requireActivity(), edt_hair_color)
                it.data.forEachIndexed { index, data ->
                    popup.menu.add(0, data.id, index, data.name)//(groupId, itemId, order, title)

                }

                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {
                        edt_hair_color.setText(p0!!.title)
                        hair = p0.itemId
                        return false
                    }
                })
                edt_hair_color.setOnClickListener {

                    popup.show()
                }
            }

        })

        viewModel.bodyResponse.observe(viewLifecycleOwner, Observer {
            (activity as HomeActivity).loading!!.dismiss()

            if (it != null && it.success) {

                val popup = PopupMenu(requireActivity(), edt_body_type)
                it.data.forEachIndexed { index, data ->
                    popup.menu.add(0, data.id, index, data.name)//(groupId, itemId, order, title)

                }

                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {
                        edt_body_type.setText(p0!!.title)
                        body = p0.itemId

                        return false
                    }
                })

                edt_body_type.setOnClickListener {

                    popup.show()
                }
            }

        })

        viewModel.skinResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && it.success) {

                val popup = PopupMenu(requireActivity(), edt_skin_color)

                it.data.forEachIndexed { index, data ->
                    popup.menu.add(0, data.id, index, data.name)//(groupId, itemId, order, title)

                }

                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {
                        edt_skin_color.setText(p0!!.title)
                        skin = p0.itemId

                        return false
                    }
                })


                edt_skin_color.setOnClickListener {

                    popup.show()
                }
            }

        })

        viewModel.sendResponse.observe(viewLifecycleOwner, Observer {
            (activity as HomeActivity).loading!!.dismiss()
            if (it.success)
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            else
                Toast.makeText(activity, " request failed", Toast.LENGTH_LONG).show()


        })

        viewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            (activity as HomeActivity).loading!!.dismiss()

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

        btn_send_notes.setOnClickListener {
            if (edt_notes.text.isEmpty()) {
                edt_notes.error = "required"
                return@setOnClickListener
            }
            if (hair == null || body == null || skin == null) {
                Toast.makeText(activity, "complete required data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            (activity as HomeActivity).loading!!.show()

            viewModel.sendAppearance(JsonObject().apply {
                addProperty("hair_colour", hair)
                addProperty("skin_colour", skin)
                addProperty("body_type", body)
                addProperty("notes", edt_notes.text.toString())
            }, "Bearer ${loginRes.data.accessToken}")
        }

    }
}