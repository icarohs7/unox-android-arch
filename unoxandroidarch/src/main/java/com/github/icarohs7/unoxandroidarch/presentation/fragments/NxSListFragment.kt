package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Try
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState
import com.github.icarohs7.unoxandroidarch.presentation.adapters.BaseBindingAdapter
import com.github.icarohs7.unoxandroidarch.presentation.adapters.useUnoxAdapter

abstract class NxSListFragment<S : MvRxState, DB : ViewDataBinding, I, IDB : ViewDataBinding> : NxFragment<S, DB>() {
    abstract val itemLayout: Int
    abstract val recycler: () -> RecyclerView
    open val layoutManager: RecyclerView.LayoutManager? = null

    override fun invalidate(): Unit = withState(viewmodel) { state ->
        super.invalidate()
        onCreateAdapter(state)
    }

    open fun onCreateAdapter(state: S) {
        recycler().useUnoxAdapter<I, IDB> {
            this@NxSListFragment.layoutManager?.let(::useLayoutManager)
            useItemLayout(this@NxSListFragment.itemLayout)
            loadList(transformDataSource(state))
            bindIndexed { index, item ->
                renderItem(item, this, index)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun loadList(items: List<I>) {
        Try {
            val adapter = recycler().adapter
            adapter as BaseBindingAdapter<I, IDB>
            adapter.submitList(items)
        }.also { println("try => $it") }
    }

    abstract fun transformDataSource(state: S): List<I>
    abstract fun renderItem(item: I, view: IDB, position: Int)
}