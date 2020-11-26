package com.gemidroid.londra.splash.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.login.ui.LoginActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import io.paperdb.Paper

class SplashActivity : AppCompatActivity() {
    private val loginRes by lazy {
        Paper.book().read("login",LoginRes())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)

        Handler(Looper.getMainLooper())
            .postDelayed({
                if (loginRes.success)
                startActivity(Intent(this, HomeActivity::class.java))
                else
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 3000L)
    }
}