package com.gemidroid.londra.home.ui.specialorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gemidroid.londra.R
import com.kaopiz.kprogresshud.KProgressHUD

class SpecialOrderActivity : AppCompatActivity() {
    var loading: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loading = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)


        setContentView(R.layout.activity_special_order)
    }
}