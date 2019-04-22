package com.github.icarohs7.app.presentation

import android.os.Bundle
import com.github.icarohs7.app.R
import com.github.icarohs7.app.databinding.ActivityMainBinding
import com.github.icarohs7.unoxandroidarch.extensions.load
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        binding.imgView.load("https://google.com", onError = R.drawable.img_placeholder_img_loading)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}
