package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.presentation.adapters.BaseBindingAdapter
import com.github.icarohs7.unoxandroidarch.presentation.adapters.useUnoxAdapter
import io.reactivex.Flowable

abstract class NxSListFragment<S, DB : ViewDataBinding, I, IDB : ViewDataBinding> : BaseStatefulFragment<S, DB>() {
    protected var adapter: BaseBindingAdapter<I, IDB>? = null
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

    override fun onStart() {
        super.onStart()
        adapter = onCreateAdapter()
    }

    override fun onGetStateStream(): Flowable<S> {
        return config.stateStream
    }

    override fun getLayout(): Int {
        return config.layout
    }

    open fun onCreateAdapter(): BaseBindingAdapter<I, IDB> {
        return config.recyclerFn().useUnoxAdapter {
            config.layoutManager?.let(::useLayoutManager)
            useItemLayout(config.itemLayout)
            bindIndexed { index, item ->
                renderItem(item, this, index)
            }
        }
    }

    fun loadItems(items: List<I>) {
        adapter?.submitList(items)
    }

    abstract fun renderItem(item: I, view: IDB, position: Int)

    class Configuration<S> {
        internal var stateStream: Flowable<S> = Flowable.empty()
        internal var layout: Int = 0
        internal var itemLayout: Int = 0
        internal var layoutManager: RecyclerView.LayoutManager? = null
        internal lateinit var recyclerFn: () -> RecyclerView

        /**
         * Stream emitting the states
         * of the fragment
         */
        fun useStateStream(stateStream: Flowable<S>) {
            this.stateStream = stateStream
        }

        /**
         * Layout used by the fragment
         */
        fun useLayout(layout: Int) {
            this.layout = layout
        }

        /**
         * Layout used by each item on the adapter
         */
        fun useItemLayout(itemLayout: Int) {
            this.itemLayout = itemLayout
        }

        /**
         * Layout manager used by the recycler view
         */
        fun useLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
            this.layoutManager = layoutManager
        }

        /**
         * Recycler view used by the adapter to
         * render the items on
         */
        fun useRecycler(recyclerFn: () -> RecyclerView) {
            this.recyclerFn = recyclerFn
        }
    }
}