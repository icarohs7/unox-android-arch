/*
 * MIT License
 *
 * Copyright (c) 2018 Icaro R D Temponi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.icarohs7.unoxandroidarch.ui.adapters

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroid.mustRunOnMainThread
import kotlinx.coroutines.launch

/**
 * Adapter based on observability and dynamic lists built using [LiveData]
 */
abstract class BaseLiveDataWatcherAdapter<T, DB : ViewDataBinding>(
        @LayoutRes itemLayout: Int,
        val dataSetObservable: LiveData<List<T>>,
        diffCallback: DiffUtil.ItemCallback<T>? = null
) : BaseBindingAdapter<T, DB>(itemLayout, diffCallback) {

    /** Observer responsible of dispatching the liveData changes to the adapter */
    val observer: Observer<List<T>> = Observer { launch { onDataSourceChange(it ?: emptyList()) } }

    /** Callback invoked when the live data changes */
    open fun onDataSourceChange(items: List<T>) {
        submitList(items)
    }

    /** Start observing the data source when attached to the recycler view */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mustRunOnMainThread { dataSetObservable.observeForever(observer) }
    }

    /** Stop observing the data source when detached from the recycler view */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mustRunOnMainThread { dataSetObservable.removeObserver(observer) }
    }
}