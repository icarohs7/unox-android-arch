package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState
import com.github.icarohs7.unoxandroidarch.presentation.adapters.BaseBindingAdapter
import com.github.icarohs7.unoxandroidarch.presentation.adapters.useUnoxAdapter
import io.reactivex.Flowable

abstract class NxSListFragment<S : MvRxState, DB : ViewDataBinding, I, IDB : ViewDataBinding> : NxFragment<S, DB>() {
    protected val config: Configuration<S> by lazy { Configuration<S>().apply(::onSetup) }

    override fun onSetup(config: NxFragment.Configuration<S>) {
        config.layout = this.config.layout
        config.stateStream = this.config.stateStream
    }

    /**
     * Used to define settings kept throughout the
     * lifecycle of the fragment
     */
    abstract fun onSetup(config: Configuration<S>)

    override fun invalidate(): Unit = withState(viewmodel) { state ->
        super.invalidate()
        config.recycler().adapter = onCreateAdapter(state)
    }

    open fun onCreateAdapter(state: S): BaseBindingAdapter<I, IDB> {
        return config.recycler().useUnoxAdapter {
            config.layoutManager?.let(::useLayoutManager)
            useItemLayout(config.itemLayout)
            bindIndexed { index, item ->
                renderItem(item, this, index)
            }
        }
    }

    abstract fun renderItem(item: I, view: IDB, position: Int)

    /**
     * Configuration class of the fragment,
     * defining the state stream, layout of
     * the fragment, layout of each adapter
     * item, layout manager of the recycler
     * view and a lambda returning the recycler
     * to be used on the fragment
     */
    class Configuration<S> {
        var stateStream: Flowable<S> = Flowable.empty()
        var layout: Int = 0
        var itemLayout: Int = 0
        var layoutManager: RecyclerView.LayoutManager? = null
        lateinit var recycler: () -> RecyclerView
    }
}