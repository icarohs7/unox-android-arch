package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.presentation.adapters.BaseBindingAdapter
import com.github.icarohs7.unoxandroidarch.presentation.adapters.useUnoxAdapter

abstract class NxListFragment<S, I, DB : ViewDataBinding, IDB : ViewDataBinding> : NxFragment<S, DB>() {
    private var adapter: BaseBindingAdapter<I, IDB>? = null

    override fun onStart() {
        super.onStart()
        adapter = onCreateAdapter()
    }

    open fun onCreateAdapter(): BaseBindingAdapter<I, IDB> {
        return getRecycler().useUnoxAdapter {
            useItemLayout(getItemLayout())
            bindIndexed { index, item ->
                renderItem(item, this, index)
            }
        }
    }

    fun loadItems(items: List<I>) {
        adapter?.submitList(items)
    }

    abstract fun renderItem(item: I, view: IDB, position: Int)
    abstract fun getRecycler(): RecyclerView
    abstract fun getItemLayout(): Int
}