package com.github.icarohs7.unoxandroidarch.ui.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<B : ViewDataBinding> : BaseScopedActivity() {
    /**
     * Initialized in [onCreate]
     */
    lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
        binding.lifecycleOwner = this
        onBindingCreated(savedInstanceState)
        afterInitialSetup()
    }

    /**
     * Called after the databinding of the fragment is set
     */
    open fun onBindingCreated(savedInstanceState: Bundle?) {
    }

    /**
     * Called after [onBindingCreated]
     */
    open fun afterInitialSetup() {
    }

    /**
     * @return layout to setup data binding.
     */
    @LayoutRes
    abstract fun getLayout(): Int
}