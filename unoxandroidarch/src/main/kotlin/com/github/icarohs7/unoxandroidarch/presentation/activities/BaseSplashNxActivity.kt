package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.core.view.isInvisible
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.databinding.ActivityBaseNxSplashBinding
import com.github.icarohs7.unoxandroidarch.extensions.animateFadeIn
import com.github.icarohs7.unoxandroidarch.state.addOnLoadingListener
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.Display
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseSplashNxActivity(
        private val version: String,
        timeout: Int = 0
) : BaseTimeoutActivity<ActivityBaseNxSplashBinding>(timeout) {

    @CallSuper
    override fun onBindingCreated(savedInstanceState: Bundle?) {
        GlobalScope.launch { AppUpdater(applicationContext).setDisplay(Display.NOTIFICATION).start() }
        super.onBindingCreated(savedInstanceState)
        addOnLoadingListener(::toggleLoading)
        binding.txtVersion.text = version
        binding.imgLogo.alpha = 0f
        binding.imgLogo.animateFadeIn((timeout).toLong())
    }

    private fun toggleLoading(isLoading: Boolean) {
        binding.progressBar.isInvisible = !isLoading
    }

    override fun getLayout(): Int {
        return R.layout.activity_base_nx_splash
    }
}