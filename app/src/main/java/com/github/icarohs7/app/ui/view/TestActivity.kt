package com.github.icarohs7.app.ui.view

import com.github.icarohs7.app.R
import com.github.icarohs7.app.databinding.ActivityTestBinding
import com.github.icarohs7.unoxandroid.ui.activities.BaseBindingActivity

class TestActivity : BaseBindingActivity<ActivityTestBinding>() {
    override fun getLayout(): Int {
        return R.layout.activity_test
    }
}