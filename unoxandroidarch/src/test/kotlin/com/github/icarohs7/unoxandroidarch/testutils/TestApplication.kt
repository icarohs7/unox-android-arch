package com.github.icarohs7.unoxandroidarch.testutils

import android.app.Application
import com.github.icarohs7.unoxandroidarch.R

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AppCompat)
    }
}