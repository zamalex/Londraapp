package com.gemidroid.londra

import android.app.Application
import com.onesignal.OneSignal
import io.paperdb.Paper
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
    }
}