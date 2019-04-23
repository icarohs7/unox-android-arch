package com.github.icarohs7.app.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.github.icarohs7.app.R
import com.github.icarohs7.app.databinding.ActivityMainBinding
import com.github.icarohs7.unoxandroidarch.extensions.context
import com.github.icarohs7.unoxandroidarch.extensions.load
import com.github.icarohs7.unoxandroidarch.extensions.requestPermissions
import com.github.icarohs7.unoxandroidarch.getCurrentLocation
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseBindingActivity
import kotlinx.coroutines.launch
import splitties.lifecycle.coroutines.awaitState

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        binding.imgView.load("https://google.com", onError = R.drawable.img_placeholder_img_loading)

        launch {
            lifecycle.awaitState(Lifecycle.State.RESUMED)
            requestPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            binding.txtView.text = getCurrentLocation(context).toString()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}
