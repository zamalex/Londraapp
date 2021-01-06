package com.gemidroid.londra

import android.app.Application
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.onesignal.OneSignal
import creativitysol.com.planstech.api.Retrofit
import io.paperdb.Paper
import io.reactivex.rxjava3.schedulers.Schedulers

const val ONESIGNAL_APP_ID = "6fdd520d-7ccf-48a9-8aae-8e47077e803e"

class AppClass : Application() {


    override fun onCreate() {
        super.onCreate()

        Paper.init(this);
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        val email = Paper.book().read("email", "")
        val password = Paper.book().read("password", "")

        if (!email.isNullOrEmpty())
            Retrofit.Api.login(JsonObject().apply {
                addProperty("email", email)
                addProperty("password", password)
            }).subscribeOn(Schedulers.io()).subscribe { t1, t2 ->
                if (t1 != null && t1.success) {
                    Paper.book().write("login", t1)
                }

            }


    }

}