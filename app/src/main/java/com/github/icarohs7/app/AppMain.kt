package com.github.icarohs7.app

import android.app.Application
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch

class AppMain : Application() {
    override fun onCreate() {
        super.onCreate()
        setupUnoxAndroid()
    }

    private fun setupUnoxAndroid() {
        UnoxAndroidArch.setActivityAndFragmentTransitionAnimation(UnoxAndroidArch.AnimationType.SWIPE_LEFT)
    }
}