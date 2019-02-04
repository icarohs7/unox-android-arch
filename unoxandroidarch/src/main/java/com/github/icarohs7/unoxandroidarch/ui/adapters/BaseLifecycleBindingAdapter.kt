package com.github.icarohs7.unoxandroidarch.ui.adapters

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroid.extensions.coroutines.cancelCoroutineScope

/**
 * Altered version of [BaseBindingAdapter] that
 * observes a given lifecycler owner and cancel its
 * coroutines when the owner gets destroyed
 */
abstract class BaseLifecycleBindingAdapter<T, DB : ViewDataBinding>(
        @LayoutRes itemLayoutRes: Int,
        parentLifecycleOwner: LifecycleOwner,
        diffCallback: DiffUtil.ItemCallback<T>? = null
) : BaseBindingAdapter<T, DB>(itemLayoutRes, diffCallback), LifecycleObserver {

    init {
        @Suppress("LeakingThis")
        parentLifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        this.cancelCoroutineScope()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    }
}