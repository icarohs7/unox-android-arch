package com.github.icarohs7.unoxandroidarch.ui.adapters

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.snakydesign.livedataextensions.liveDataOf
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Object containig short hand builders for
 * adapters using a builder dsl
 */
object DslAdapters {

    /**
     * Builder used to create a
     * [BaseObservableWatcherAdapter]
     */
    fun <T, DB : ViewDataBinding> rxBinding(
            @LayoutRes itemLayout: Int,
            itemBind: DslAdapterBuilderRx<T, DB>.() -> Unit
    ): BaseObservableWatcherAdapter<T, DB> {
        val builder = object : DslAdapterBuilderRx<T, DB> {
            override var dataSource: List<T> = emptyList()
            override var observable: Observable<List<T>> = Observable.empty()
            override var onItemBind: DB.(T) -> Unit = {}
        }

        builder.itemBind()
        val observable = when (builder.dataSource.isEmpty()) {
            true -> builder.observable
            false -> Observable.just(builder.dataSource)
        }

        return object : BaseObservableWatcherAdapter<T, DB>(itemLayout, observable) {
            override fun onBindItemToView(item: T, view: DB) {
                builder.onItemBind(view, item)
            }
        }
    }

    /**
     * Builder used to create a
     * [BaseLiveDataWatcherAdapter]
     */
    fun <T, DB : ViewDataBinding> liveDataBinding(
            @LayoutRes itemLayout: Int,
            itemBind: DslAdapterBuilderLiveData<T, DB>.() -> Unit
    ): BaseLiveDataWatcherAdapter<T, DB> {
        val builder = object : DslAdapterBuilderLiveData<T, DB> {
            override var dataSource: List<T> = emptyList()
            override var observable: LiveData<List<T>> = MutableLiveData()
            override var onItemBind: DB.(T) -> Unit = {}
        }

        builder.itemBind()
        val observable = when (builder.dataSource.isEmpty()) {
            true -> builder.observable
            false -> runBlocking(Dispatchers.Main) { liveDataOf(builder.dataSource) }
        }

        return object : BaseLiveDataWatcherAdapter<T, DB>(itemLayout, observable) {
            override fun onBindItemToView(item: T, view: DB) {
                builder.onItemBind(view, item)
            }
        }
    }

    interface DslAdapterBuilderRx<T, DB : ViewDataBinding> {
        var dataSource: List<T>
        var observable: Observable<List<T>>
        var onItemBind: DB.(T) -> Unit
    }

    interface DslAdapterBuilderLiveData<T, DB : ViewDataBinding> {
        var dataSource: List<T>
        var observable: LiveData<List<T>>
        var onItemBind: DB.(T) -> Unit
    }
}