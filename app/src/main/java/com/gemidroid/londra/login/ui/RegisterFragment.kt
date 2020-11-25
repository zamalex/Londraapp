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
import kotlinx.android.synthetic.main.fragment_register.*

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
            //startActivity(Intent(requireActivity(), HomeActivity::class.java))
            //requireActivity().finish()
            Loading.initLoading(requireActivity())

            if (validateInputs()){

                Loading.show()
                registerViewModel.register(JsonObject().apply {
                    addProperty("first_name",edt_profile.text.toString())
                    addProperty("last_name","")
                    addProperty("email",edt_email.text.toString())
                    addProperty("password",edt_password.text.toString())
                    addProperty("mobile",edt_phone.text.toString())
                    addProperty("country_code","SA")
                })
            }

        }

        setResponse()
        setError()
    }
    fun setResponse() = registerViewModel.getResponse.observe(viewLifecycleOwner, Observer {
        Loading.dismiss()
        Toast.makeText(activity, it.string(), Toast.LENGTH_SHORT).show()
    })

    fun setError() = registerViewModel.getError.observe(viewLifecycleOwner, Observer {
        Loading.dismiss()

        if (it != null)
            Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()


    })
    fun validateInputs():Boolean{
        if (edt_profile.text.isEmpty()){
            edt_profile.error = "enter name"
            return false
        }
        if (!Validator.isValidEmail(edt_email.text)){
            edt_email.error = "enter mail"
            return false
        }
        if (!Validator.isValidPhone(edt_phone.text)){
            edt_phone.error = "enter valid phone"
            return false
        }
        if (edt_password.text.isEmpty()){
            edt_password.error = "enter name"
            return false
        }
        return true
    }
}
