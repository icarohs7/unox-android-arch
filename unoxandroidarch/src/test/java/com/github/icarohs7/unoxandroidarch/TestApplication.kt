package com.github.icarohs7.unoxandroidarch

import android.app.Application

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AppCompat)
    }
}