package com.gemidroid.londra

import android.app.Application
import io.paperdb.Paper

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()

        Paper.init(this);

    }
}