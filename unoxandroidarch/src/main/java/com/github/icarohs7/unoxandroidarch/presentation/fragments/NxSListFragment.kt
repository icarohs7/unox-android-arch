package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.presentation.adapters.BaseBindingAdapter
import com.github.icarohs7.unoxandroidarch.presentation.adapters.useUnoxAdapter
import io.reactivex.Flowable

abstract class NxSListFragment<S, DB : ViewDataBinding, I, IDB : ViewDataBinding> : BaseStatefulFragment<S, DB>() {
    protected var adapter: BaseBindingAdapter<I, IDB>? = null
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
        return config.recycler.useUnoxAdapter {
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
        internal lateinit var recycler: RecyclerView

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
         * Recycler view used by the adapter to
         * render the items on
         */
        fun useRecycler(recycler: RecyclerView) {
            this.recycler = recycler
        }
    }
}