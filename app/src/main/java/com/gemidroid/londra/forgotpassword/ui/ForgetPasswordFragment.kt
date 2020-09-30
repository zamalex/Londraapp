package com.gemidroid.londra.forgotpassword.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gemidroid.londra.R
import com.gemidroid.londra.forgotpassword.ui.ForgetPasswordActivity.Companion.EMAIL_KEY
import kotlinx.android.synthetic.main.fragment_forget_password.*

class ForgetPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_send_code.setOnClickListener {

            if (TextUtils.isEmpty(edt_email.text.toString()))
                edt_email.error = getString(R.string.email_empty)
            else {
                val bundle = bundleOf(EMAIL_KEY to edt_email.text.toString())
                findNavController().navigate(
                    R.id.action_forgetPasswordFragment_to_updatePassword,
                    bundle
                )
            }
        }
    }

}