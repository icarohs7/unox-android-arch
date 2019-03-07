package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import io.reactivex.Flowable

abstract class NxFragment<S, DB : ViewDataBinding> : BaseStatefulFragment<S, DB>() {
    protected val config: Configuration<S> by lazy { Configuration<S>().apply(::onSetup) }

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