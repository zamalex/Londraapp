package com.gemidroid.londra.forgotpassword.ui

import android.content.Intent
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
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_update_password.*

class UpdatePassword : Fragment() {
    private val viewModel: ForgotViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(EMAIL_KEY).let {
            txt_change_password.text =
                String.format(
                    getString(R.string.txt_change_password_msg),
                    it
                )
        }

        btn_change_password.setOnClickListener {
            when {
                TextUtils.isEmpty(edt_password.text) -> edt_password.error =
                    getString(R.string.enter_password)
                TextUtils.isEmpty(edt_password_repeat.text) -> edt_password_repeat.error =
                    getString(R.string.enter_password)
                !TextUtils.equals(
                    edt_password.text,
                    edt_password_repeat.text
                ) -> edt_password_repeat.error = getString(R.string.password_mismatch)
                else -> {
                    (activity as ForgetPasswordActivity).loading!!.show()
                    viewModel.resetPass(JsonObject().apply {
                        addProperty("email", arguments?.getString(EMAIL_KEY))
                        addProperty("password", edt_password.text.toString())
                    })

                }
            }
        }

        viewModel.updateres.observe(viewLifecycleOwner, Observer {
            (activity as ForgetPasswordActivity).loading!!.dismiss()
            if (it != null && it.success) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        })
        viewModel.errorres.observe(viewLifecycleOwner, Observer {
            (activity as ForgetPasswordActivity).loading!!.dismiss()

            if (it != null)
                Toast.makeText(requireActivity(), "request failed", Toast.LENGTH_LONG).show()


        })
    }
}