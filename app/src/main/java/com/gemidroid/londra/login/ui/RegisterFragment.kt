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
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.login.ui.viewmodel.RegisterViewModel
import com.gemidroid.londra.utils.Validator
import com.google.gson.JsonObject
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.HttpException
import java.net.UnknownHostException

class RegisterFragment : Fragment() {
    val registerViewModel by activityViewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener {

            if (validateInputs()) {

                (activity as LoginActivity).loading!!.show()
                registerViewModel.register(JsonObject().apply {
                    addProperty("first_name", edt_profile.text.toString())
                    addProperty("last_name", edt_profile_last.text.toString())
                    addProperty("email", edt_email.text.toString())
                    addProperty("password", edt_password.text.toString())
                    addProperty("mobile", edt_phone.text.toString())
                    addProperty("country_code", "SA")
                })
            }

        }

        setResponse()
        setError()
    }

    fun setResponse() = registerViewModel.getResponse.observe(viewLifecycleOwner, Observer {
        (activity as LoginActivity).loading!!.dismiss()
        if (it != null && it.success) {
            Paper.book().write("login", it)
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()

        }
    })

    fun setError() = registerViewModel.getError.observe(viewLifecycleOwner, Observer {
        (activity as LoginActivity).loading!!.dismiss()

        if (it != null) {
            if (it is UnknownHostException)
                Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
            else if (it is HttpException)

                Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
        }

    })

    fun validateInputs(): Boolean {
        if (edt_profile.text.isEmpty()) {
            edt_profile.error = "enter first name"
            return false
        }

        if (edt_profile_last.text.isEmpty()) {
            edt_profile_last.error = "enter last name"
            return false
        }
        if (!Validator.isValidEmail(edt_email.text)) {
            edt_email.error = "enter mail"
            return false
        }
        if (!Validator.isValidPhone(edt_phone.text)) {
            edt_phone.error = "enter valid phone"
            return false
        }
        if (edt_password.text.isEmpty()) {
            edt_password.error = "enter name"
            return false
        }
        return true
    }
}
