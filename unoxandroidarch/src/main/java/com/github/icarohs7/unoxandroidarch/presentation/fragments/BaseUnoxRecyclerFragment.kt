package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.presentation.adapters.BaseFlowableWatcherAdapter
import com.github.icarohs7.unoxandroidarch.presentation.adapters.UnoxAdapterBuilder
import com.github.icarohs7.unoxandroidarch.presentation.adapters.useUnoxAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import kotlinx.coroutines.launch

/**
 * Derived class of [BaseRecyclerFragment] setup
 * using the UnoxAdapterBuilder
 * @param T Type of item shown on the recycler view
 * @param DB Type of databinding of the recycler item
 */
abstract class BaseUnoxRecyclerFragment<T, DB : ViewDataBinding> : BaseRecyclerFragment() {
    var adapter: BaseFlowableWatcherAdapter<T, DB>? = null
    abstract suspend fun onSetup(builder: UnoxAdapterBuilder<T, DB>)

    override fun onRecyclerSetup(recycler: RecyclerView) {
        launch {
            adapter = recycler.useUnoxAdapter {
                onSetup(this)
                observeLoadedItems(flowable)
            }
        }
    }

    private fun observeLoadedItems(flowable: Flowable<List<T>>) {
        flowable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onItemsLoaded)
                .disposeBy(onDestroy)
    }

    open fun onItemsLoaded(items: List<T>) {
        if (items.isNotEmpty()) binding.stateView.hideStates()
    }
}