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
    abstract val stateStream: Flowable<S>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.stateStream = stateStream
    }
}