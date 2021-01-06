package com.gemidroid.londra.home.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.notifications.NotificationsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var loading: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_home)

        loading = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            txt_main_title.text = destination.label
        }

        navView.setupWithNavController(navController)



        img_notifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }


    }
}