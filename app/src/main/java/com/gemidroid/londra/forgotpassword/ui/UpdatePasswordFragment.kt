package com.gemidroid.londra.forgotpassword.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import com.gemidroid.londra.forgotpassword.ui.ForgetPasswordActivity.Companion.EMAIL_KEY
import com.gemidroid.londra.login.ui.LoginActivity
import kotlinx.android.synthetic.main.fragment_update_password.*

class UpdatePassword : Fragment() {

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
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }
}