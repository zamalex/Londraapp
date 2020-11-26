package com.gemidroid.londra.forgotpassword.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gemidroid.f3aleity.utils.Loading
import com.gemidroid.londra.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_check_code.*
import kotlinx.android.synthetic.main.fragment_check_code.btn_send_code
import kotlinx.android.synthetic.main.fragment_forget_password.*
import kotlinx.android.synthetic.main.fragment_update_password.*


class CheckCodeFragment : Fragment() {

    private val viewModel: ForgotViewModel by activityViewModels()
    private var email: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(ForgetPasswordActivity.EMAIL_KEY).let {
            email = it
            txt_enter_code.text =
                String.format(
                    getString(R.string.pass_code),
                    it
                )
        }


        btn_send_code.setOnClickListener {
            if (edt_code.text.isEmpty()) {
                edt_code.error = "enter code"
                return@setOnClickListener
            }
            (activity as ForgetPasswordActivity).loading!!.show()
            viewModel.checkCode(JsonObject().apply {
                addProperty("email", arguments?.getString(ForgetPasswordActivity.EMAIL_KEY))
                addProperty("token", edt_code.text.toString())
            })
        }

        viewModel.checkCode.observe(viewLifecycleOwner, Observer {
            (activity as ForgetPasswordActivity).loading!!.dismiss()

            if (it != null && it.success) {
                val bundle = bundleOf(ForgetPasswordActivity.EMAIL_KEY to email)
                findNavController().navigate(
                    R.id.action_checkCodeFragment_to_updatePassword,
                    bundle
                )
            }
        })
        viewModel.setError.observe(viewLifecycleOwner, Observer {
            (activity as ForgetPasswordActivity).loading!!.dismiss()

            if (it != null)
                Toast.makeText(requireActivity(), "wrong code", Toast.LENGTH_LONG).show()


        })
    }


}