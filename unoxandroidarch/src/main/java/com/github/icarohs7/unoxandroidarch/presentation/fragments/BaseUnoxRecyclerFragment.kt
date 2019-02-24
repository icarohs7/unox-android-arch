package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.presentation.adapters.BaseBindingAdapter
import com.github.icarohs7.unoxandroidarch.presentation.adapters.UnoxAdapterBuilder
import com.github.icarohs7.unoxandroidarch.presentation.adapters.useUnoxAdapter

/**
 * Derived class of [BaseRecyclerFragment] setup
 * using the UnoxAdapterBuilder
 * @param T Type of item shown on the recycler view
 * @param DB Type of databinding of the recycler item
 */
abstract class BaseUnoxRecyclerFragment<T, DB : ViewDataBinding> : BaseRecyclerFragment() {
    /**
     * Created on [onBindingCreated],
     * Before [afterInitialSetup]
     */
    lateinit var adapter: BaseBindingAdapter<T, DB>

    /**
     * Used to setup the adapter that
     * will be used by the fragment
     * through the builder
     */
    abstract fun onCreateAdapter(builder: UnoxAdapterBuilder<T, DB>)

    override fun onRecyclerSetup(recycler: RecyclerView) {
        adapter = recycler.useUnoxAdapter {
            onCreateAdapter(this)
        }
    }

    /**
     * Load items into the adapter
     */
    fun loadItems(items: List<T>) {
        adapter.submitList(items)
        onItemsLoaded(items)
    }

    /**
     * Callback called each time
     * items are loaded into the
     * adapter through [loadItems]
     */
    open fun onItemsLoaded(items: List<T>) {
        if (items.isNotEmpty()) binding.stateView.hideStates()
    }
}