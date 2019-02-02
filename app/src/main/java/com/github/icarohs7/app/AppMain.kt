package com.github.icarohs7.app

import android.app.Application
import com.github.icarohs7.unoxandroid.UnoxAndroid

class AppMain : Application() {
    override fun onCreate() {
        super.onCreate()
        setupUnoxAndroid()
    }

    private fun setupUnoxAndroid() {
        UnoxAndroid.setActivityAndFragmentTransitionAnimation(UnoxAndroid.AnimationType.SWIPE_LEFT)
    }
}