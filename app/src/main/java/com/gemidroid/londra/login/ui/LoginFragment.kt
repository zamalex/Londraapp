package com.gemidroid.londra.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.f3aleity.utils.Loading
import com.gemidroid.londra.R
import com.gemidroid.londra.api.SuccessResponse
import com.gemidroid.londra.forgotpassword.ui.ForgetPasswordActivity
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.login.ui.viewmodel.LoginViewModel
import com.gemidroid.londra.utils.Validator
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.HttpException
import java.net.UnknownHostException

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


        btn_login.setOnClickListener {
            if (!Validator.isValidEmail(edt_email.text)) {
                edt_email.error = "enter valid email"
                return@setOnClickListener
            }

            if (edt_password.text.isEmpty()) {
                edt_password.error = "enter valid password"
                return@setOnClickListener
            }


            (activity as LoginActivity).loading!!.show()
            loginViewModel.login(JsonObject().apply {
                addProperty("email", edt_email.text.toString())
                addProperty("password", edt_password.text.toString())
            })
        }

        setResponse()
        setError()

    }

    fun setResponse() = loginViewModel.getLoginResponse.observe(viewLifecycleOwner, Observer {
        (activity as LoginActivity).loading!!.dismiss()
        if (it != null && it.success) {
            Paper.book().write("login", it)

            Paper.book().write("email", edt_email.text.toString())
            Paper.book().write("password", edt_password.text.toString())

            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()

        }


    })

    fun setError() = loginViewModel.getError.observe(viewLifecycleOwner, Observer {
        (activity as LoginActivity).loading!!.dismiss()

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
}
