package com.gemidroid.londra.forgotpassword.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gemidroid.f3aleity.utils.Loading
import com.gemidroid.londra.R
import com.kaopiz.kprogresshud.KProgressHUD

class ForgetPasswordActivity : AppCompatActivity() {
    var loading: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        loading = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    companion object {
        val EMAIL_KEY: String = "email"
    }

}