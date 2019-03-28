package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.reactivex.Flowable

abstract class NxFragment<S, DB : ViewDataBinding> : BaseStatefulFragment<S, DB>() {
    protected val config: Configuration<S> by lazy { Configuration<S>().apply(::onSetup) }

    override fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        super.onBindingCreated(inflater, container, savedInstanceState)
        renderOnce()
    }

    /**
     * Called once when the fragment is created,
     * not being recalculated when the state change
     */
    open fun renderOnce() {
    }

    /**
     * Used to define settings kept throughout the
     * lifecycle of the fragment
     */
    abstract fun onSetup(config: Configuration<S>)

    override fun onNewState(state: S) {
        render(state)
    }

    /**
     * Define the render logic, being called
     * everytime the state changes
     */
    abstract fun render(state: S)

    override fun onGetStateStream(): Flowable<S> {
        return config.stateStream
    }

    override fun getLayout(): Int {
        return config.layout
    }

    class Configuration<S> {
        /**
         * Stream emitting the states
         * of the fragment
         */
        var stateStream: Flowable<S> = Flowable.empty()

        /**
         * Layout used by the fragment
         */
        var layout: Int = 0
    }
}