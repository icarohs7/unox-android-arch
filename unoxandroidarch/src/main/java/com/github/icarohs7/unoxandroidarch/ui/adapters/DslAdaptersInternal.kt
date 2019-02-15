package com.github.icarohs7.unoxandroidarch.ui.adapters

import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.snakydesign.livedataextensions.liveDataOf
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

private typealias OBS<T> = Observable<List<T>>
private typealias LDT<T> = LiveData<List<T>>

/**
 * Builder used to create a
 * [BaseObservableWatcherAdapter]
 */
@MainThread
inline fun <T, DB : ViewDataBinding> RecyclerView.showObservable(
        @LayoutRes itemLayout: Int,
        itemBind: DslAdaptersInternal.DslAdapterBuilder<T, OBS<T>, DB>.() -> Unit
) {
    val builder = DslAdaptersInternal.emptyObservableBuilder<T, DB>().apply(itemBind)
    this.adapter = DslAdaptersInternal.rxBinding(itemLayout, builder)
}

/**
 * Builder used to create a
 * [BaseLiveDataWatcherAdapter]
 */
@MainThread
inline fun <T, DB : ViewDataBinding> RecyclerView.showLiveData(
        @LayoutRes itemLayout: Int,
        itemBind: DslAdaptersInternal.DslAdapterBuilder<T, LiveData<List<T>>, DB>.() -> Unit
) {
    val builder = DslAdaptersInternal.emptyLiveDataBuilder<T, DB>().apply(itemBind)
    this.adapter = DslAdaptersInternal.liveDataBinding(itemLayout, builder)
}

object DslAdaptersInternal {

    fun <T, DB : ViewDataBinding> rxBinding(
            @LayoutRes itemLayout: Int,
            builder: DslAdapterBuilder<T, Observable<List<T>>, DB>
    ): BaseObservableWatcherAdapter<T, DB> {
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

    fun <T, DB : ViewDataBinding> liveDataBinding(
            @LayoutRes itemLayout: Int,
            builder: DslAdapterBuilder<T, LiveData<List<T>>, DB>
    ): BaseLiveDataWatcherAdapter<T, DB> {
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

    fun <T, DB : ViewDataBinding> emptyObservableBuilder(): DslAdapterBuilder<T, OBS<T>, DB> {
        return EmptyBuilder(Observable.empty())
    }

    fun <T, DB : ViewDataBinding> emptyLiveDataBuilder(): DslAdapterBuilder<T, LDT<T>, DB> {
        return EmptyBuilder(MutableLiveData())
    }

    interface DslAdapterBuilder<T, O, DB : ViewDataBinding> {
        var dataSource: List<T>
        var observable: O
        var onItemBind: DB.(T) -> Unit
    }

    internal class EmptyBuilder<T, O, DB : ViewDataBinding>(
            emptyObservable: O
    ) : DslAdapterBuilder<T, O, DB> {
        override var dataSource: List<T> = emptyList()
        override var observable: O = emptyObservable
        override var onItemBind: DB.(T) -> Unit = {}
    }
}