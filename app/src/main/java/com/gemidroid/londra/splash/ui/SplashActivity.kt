package com.gemidroid.londra.splash.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gemidroid.londra.R
import com.gemidroid.londra.login.ui.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)

        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 3000L)
    }
}