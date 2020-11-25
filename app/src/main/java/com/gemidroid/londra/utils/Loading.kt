package com.gemidroid.f3aleity.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

object Loading {
    lateinit var context: Context
     var loading:KProgressHUD?=null
    fun initLoading(context: Context){
        this.context = context
        loading = KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    fun show(){
        loading?.show()
    }
    fun dismiss(){
        loading?.dismiss()
    }


}