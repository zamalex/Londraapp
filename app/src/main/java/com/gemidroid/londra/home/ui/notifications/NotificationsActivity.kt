package com.gemidroid.londra.home.ui.notifications

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.gemidroid.londra.R
import com.kaopiz.kprogresshud.KProgressHUD

class NotificationsActivity : AppCompatActivity() {
    var loading: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)


        loading = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
    }
}