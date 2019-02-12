package com.github.icarohs7.unoxandroidarch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.github.icarohs7.unoxandroid.extensions.coroutines.cancelCoroutineScope
import com.github.icarohs7.unoxandroidarch.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.jetbrains.anko.matchParent


/**
 * Base implementation of a dialog fragment
 * with a [CoroutineScope] and using view
 * binding to inflate its view
 */
abstract class BaseBindingDialogFragment<DB : ViewDataBinding> : DialogFragment(), CoroutineScope by MainScope() {
    /**
     * Initialized on [onCreateView]
     */
    lateinit var binding: DB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
                .inflate<DB>(inflater, getLayout(), container, false)
                .apply { lifecycleOwner = this@BaseBindingDialogFragment }

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
     * Make the dialog fullscreen on start
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(matchParent, matchParent)
        dialog?.window?.setWindowAnimations(R.style.AppTheme_Slide)
    }

    /**
     * @return layout to setup data binding.
     */
    @LayoutRes
    abstract fun getLayout(): Int

    override fun onDestroy() {
        cancelCoroutineScope()
        super.onDestroy()
    }
}