package com.gemidroid.londra.home.ui.department

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gemidroid.londra.R
import com.kaopiz.kprogresshud.KProgressHUD

class DepartmentActivity : AppCompatActivity() {
    var loading: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)

        loading = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
    }
}