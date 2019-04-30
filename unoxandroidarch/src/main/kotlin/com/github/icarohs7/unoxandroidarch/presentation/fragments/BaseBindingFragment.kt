package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Base fragment using databinding
 */
abstract class BaseBindingFragment<B : ViewDataBinding> : BaseScopedFragment() {
    /**
     * Initialized on [onCreateView]
     */
    lateinit var binding: B
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
                .inflate<B>(inflater, getLayout(), container, false)
                .apply { lifecycleOwner = this@BaseBindingFragment }

        onBindingCreated(inflater, container, savedInstanceState)
        afterInitialSetup()
        return binding.root
    }

    /**
     * Called after the databinding of the fragment is set
     */
    open fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
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