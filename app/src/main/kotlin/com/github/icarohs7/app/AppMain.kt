package com.github.icarohs7.app

import android.app.Application
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import org.koin.core.context.startKoin

class AppMain : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {}
        UnoxAndroidArch.init()
    }
}