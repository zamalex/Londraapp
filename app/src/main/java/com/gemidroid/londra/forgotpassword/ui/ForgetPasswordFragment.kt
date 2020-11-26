package com.gemidroid.londra.forgotpassword.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gemidroid.f3aleity.utils.Loading
import com.gemidroid.londra.R
import com.gemidroid.londra.forgotpassword.ui.ForgetPasswordActivity.Companion.EMAIL_KEY
import com.gemidroid.londra.login.ui.LoginActivity
import com.gemidroid.londra.utils.Validator
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_forget_password.*

class ForgetPasswordFragment : Fragment() {
    private val viewModel : ForgotViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_send_code.setOnClickListener {

            if (!Validator.isValidEmail(edt_email.text.toString()))
                edt_email.error = getString(R.string.email_empty)
            else {
                (activity as ForgetPasswordActivity).loading!!.show()

                viewModel.sendMeCode(JsonObject().apply {
                    addProperty("email",edt_email.text.toString())
                })

            }
        }

        viewModel.sendMeCodeRes.observe(viewLifecycleOwner, Observer {
            (activity as ForgetPasswordActivity).loading!!.dismiss()
            if (it!=null&&it.success){
                val bundle = bundleOf(EMAIL_KEY to edt_email.text.toString())
                findNavController().navigate(
                    R.id.action_forgetPasswordFragment_to_checkCodeFragment,
                    bundle
                )
            }else{
                Toast.makeText(requireActivity(),"request failed",Toast.LENGTH_LONG).show()
            }
        })
    }




}