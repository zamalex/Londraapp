package com.gemidroid.londra.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gemidroid.londra.R
import com.gemidroid.londra.forgotpassword.ui.ForgetPasswordActivity
import com.gemidroid.londra.home.ui.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_forgot_pass.setOnClickListener {
            startActivity(Intent(activity, ForgetPasswordActivity::class.java))
        }

        btn_login.setOnClickListener {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        }
    }
}
