package com.github.icarohs7.unoxandroidarch.testutils

import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.databinding.MockViewBinding
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseTimeoutActivity

class TestBaseTimeoutActivity : BaseTimeoutActivity<MockViewBinding>(0, checkAppUpdate = true) {
    var someVariable: Int = 0

    override fun onTimeout() {
        someVariable = 1532
    }

    override fun getLayout(): Int {
        return R.layout.mock_view
    }
}