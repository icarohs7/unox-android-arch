package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import io.reactivex.Flowable

abstract class NxActivity<S, DB : ViewDataBinding> : BaseStatefulActivity<S, DB>() {
    protected val config: Configuration<S> by lazy { Configuration<S>().apply(::onSetup) }

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
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
        internal var stateStream: Flowable<S> = Flowable.empty()
        internal var layout: Int = 0

        /**
         * Layout used by the fragment
         */
        fun useLayout(layout: Int) {
            this.layout = layout
        }

        /**
         * Stream emitting the states
         * of the fragment
         */
        fun useStateStream(stateStream: Flowable<S>) {
            this.stateStream = stateStream
        }
    }
}