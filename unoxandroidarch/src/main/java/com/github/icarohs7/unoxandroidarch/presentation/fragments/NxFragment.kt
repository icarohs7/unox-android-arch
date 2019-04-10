package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.airbnb.mvrx.MvRxState
import com.github.icarohs7.unoxandroidarch.presentation.viewmodel.SimpleRxMvRxViewModel
import io.reactivex.Flowable

/**
 * Fragment extending [BaseBindingFragment] subscribing
 * to a [Flowable] and connecting its emissions to its
 * state
 */
abstract class NxFragment<S : MvRxState, DB : ViewDataBinding> : BaseBindingFragment<DB>() {
    abstract val viewmodel: SimpleRxMvRxViewModel<S>
    private val config: Configuration<S> by lazy { Configuration<S>().apply(::onSetup) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.stateStream = config.stateStream
    }

    abstract fun onSetup(config: Configuration<S>)

    override fun getLayout(): Int = config.layout

    /**
     * Class defining the settings used
     * by the fragment, e.g layout resource
     * and state stream
     */
    class Configuration<S : MvRxState> {
        var layout: Int = 0
        lateinit var stateStream: Flowable<S>
    }
}