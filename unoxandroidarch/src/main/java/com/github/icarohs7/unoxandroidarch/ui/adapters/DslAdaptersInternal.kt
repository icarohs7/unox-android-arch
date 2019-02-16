package com.github.icarohs7.unoxandroidarch.ui.adapters

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.toPublisher
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.state.Reducer
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.coroutines.launch

/**
 * Builder used to create a multi purpose adapter
 * and use it on the given [RecyclerView]
 * using a DSL
 */
@MainThread
fun <T, DB : ViewDataBinding> RecyclerView.useUnoxAdapter(
        builderBlock: DslAdaptersInternal.Builder<T, DB>.() -> Unit
): BaseFlowableWatcherAdapter<T, DB> {
    val builder = DslAdaptersInternal.Builder<T, DB>(context)
    builderBlock(builder)
    return DslAdaptersInternal.buildAdapterAndSetupRecycler(this, builder)
}


object DslAdaptersInternal {

    /**
     * Function responsible of creating the adapter,
     * applying the builder and returning it
     */
    internal fun <T, DB : ViewDataBinding> buildAdapterAndSetupRecycler(
            recyclerView: RecyclerView,
            builder: Builder<T, DB>
    ): BaseFlowableWatcherAdapter<T, DB> {
        val (layout, flowable, diffCallback) = with(builder) { Triple(itemLayout, flowable, diffCallback) }

        val adapter = object : BaseFlowableWatcherAdapter<T, DB>(layout, flowable, diffCallback) {
            override fun onBindItemToView(index: Int, item: T, view: DB) {
                launch { builder.bindFun(view, index, item) }
            }
        }

        recyclerView.layoutManager = builder.layoutManager
        recyclerView.adapter = adapter

        return adapter.run(builder.adapterSetup)
    }

    /**
     * Builder used to make a [BaseFlowableWatcherAdapter]
     * through a DSL
     */
    class Builder<T, DB : ViewDataBinding>(val context: Context) {
        internal var flowable: Flowable<List<T>> = Flowable.just(emptyList())
        internal var bindFun: suspend DB.(index: Int, item: T) -> Unit = { _, _ -> }
        internal var itemLayout: Int = 0
        internal var diffCallback: DiffUtil.ItemCallback<T>? = null
        internal var adapterSetup: Reducer<BaseFlowableWatcherAdapter<T, DB>> = { this }
        internal var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        /**
         * Define the layout used by each
         * item of the adapter
         */
        fun useItemLayout(@LayoutRes itemLayout: Int) {
            this.itemLayout = itemLayout
        }

        /**
         * Define the function used to map an
         * item to a view
         */
        fun bind(bindFun: suspend DB.(item: T) -> Unit) {
            this.bindFun = { _, item -> bindFun(item) }
        }

        /**
         * Define the function used to map an
         * item to a view using the item index
         * in the adapter
         */
        fun bindIndexed(bindFun: suspend DB.(index: Int, item: T) -> Unit) {
            this.bindFun = bindFun
        }

        /**
         * Use the given flowable as the data
         * source of the adapter, updating
         * its items everytime an emission
         * happens
         */
        fun observeFlowable(flowable: Flowable<List<T>>) {
            val combiner = BiFunction<List<T>, List<T>, List<T>> { a, b -> a + b }
            this.flowable = Flowable.combineLatest(flowable, this.flowable, combiner)
        }

        /**
         * Use the given observable as the data
         * source of the adapter, updating
         * its items everytime an emission
         * happens
         */
        fun observeObservable(observable: Observable<List<T>>) {
            observeFlowable(observable.toFlowable(BackpressureStrategy.LATEST))
        }

        /**
         * Use the given livedata as the data
         * source of the adapter, updating
         * its items everytime an emission
         * happens
         */
        fun observeLiveData(lifecycle: LifecycleOwner, liveData: LiveData<List<T>>) {
            observeFlowable(Flowable.fromPublisher(liveData.toPublisher(lifecycle)))
        }

        /**
         * Use the given list as the static
         * data source of the adapter
         */
        fun loadList(items: List<T>) {
            observeFlowable(Flowable.just(items))
        }

        /**
         * Use the given [DiffUtil.ItemCallback] to
         * calculate the differences between 2 lists,
         * default is [BaseBindingAdapter.AllRefreshDiffCallback]
         */
        fun useDiffCallback(diffCallback: DiffUtil.ItemCallback<T>) {
            this.diffCallback = diffCallback
        }

        /**
         * Used to modify the default adapter or
         * to use another adapter
         */
        fun configAdapter(adapterSetup: Reducer<BaseFlowableWatcherAdapter<T, DB>>) {
            this.adapterSetup = adapterSetup
        }

        /**
         * Modify the layout manager used by the
         * recycler view, default is [LinearLayoutManager]
         */
        fun useLayoutManager(layoutManager: RecyclerView.LayoutManager) {
            this.layoutManager = layoutManager
        }
    }
}