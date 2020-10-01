package com.gemidroid.londra.home.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gemidroid.londra.R
import com.gemidroid.londra.home.ui.notifications.NotificationsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_home)

        navView.setOnNavigationItemSelectedListener {
            txt_main_title.text = it.title
            true
        }

        navView.setupWithNavController(navController)



        img_notifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }
    }
}