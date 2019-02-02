package com.github.icarohs7.app.ui.view

import com.github.icarohs7.app.R
import com.github.icarohs7.app.databinding.ActivityTestBinding
import com.github.icarohs7.unoxandroid.extensions.views.navigateTo
import com.github.icarohs7.unoxandroid.ui.activities.BaseTimeoutActivity

class TestActivityTimeout : BaseTimeoutActivity<ActivityTestBinding>(600) {
    override fun onTimeout() {
        navigateTo(TestActivity::class, finishActivity = true)
    }

    override fun getLayout(): Int {
        return R.layout.activity_test
    }
}