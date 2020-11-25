package com.gemidroid.londra.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.f3aleity.utils.Loading
import com.gemidroid.londra.R
import com.gemidroid.londra.forgotpassword.ui.ForgetPasswordActivity
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.login.ui.viewmodel.LoginViewModel
import com.gemidroid.londra.utils.Validator
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    val loginViewModel: LoginViewModel by activityViewModels()

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

        Loading.initLoading(requireActivity())

        btn_login.setOnClickListener {
            if (!Validator.isValidEmail(edt_email.text)) {
                edt_email.error = "enter valid email"
                return@setOnClickListener
            }

            if (edt_password.text.isEmpty()) {
                edt_password.error = "enter valid password"
                return@setOnClickListener
            }

            // startActivity(Intent(requireActivity(), HomeActivity::class.java))
            // requireActivity().finish()
            Loading.show()
            loginViewModel.login(JsonObject().apply {
                addProperty("email", edt_email.text.toString())
                addProperty("password", edt_password.text.toString())
            })
        }

        setResponse()
        setError()

    }

    fun setResponse() = loginViewModel.getLoginResponse.observe(viewLifecycleOwner, Observer {
        Loading.dismiss()
        Toast.makeText(activity, it.string(), Toast.LENGTH_SHORT).show()
    })

    fun setError() = loginViewModel.getError.observe(viewLifecycleOwner, Observer {
        Loading.dismiss()

        if (it != null)
            Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()


    })
}
