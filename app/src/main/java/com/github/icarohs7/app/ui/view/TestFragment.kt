package com.github.icarohs7.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.icarohs7.app.R
import com.github.icarohs7.unoxandroid.ui.fragments.BaseScopedFragment

class TestFragment : BaseScopedFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, null, false)
    }
}