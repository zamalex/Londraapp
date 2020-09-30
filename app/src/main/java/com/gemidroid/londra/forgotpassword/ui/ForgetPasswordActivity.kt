package com.gemidroid.londra.forgotpassword.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gemidroid.londra.R

class ForgetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    companion object
    {
        val EMAIL_KEY: String = "email"
    }

}