package com.github.icarohs7.unoxandroidarch.ui.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.ui.adapters.DslAdaptersInternal
import com.github.icarohs7.unoxandroidarch.ui.adapters.useUnoxAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy

/**
 * Derived class of [BaseRecyclerFragment] observing
 * a RxJava Flowable, with built-in support for
 * state view to be shown when the data set is empty
 * @param T Type of item shown on the recycler view
 * @param DB Type of databinding of the recycler item
 */
abstract class BaseFlowableWatcherRecyclerFragment<T, DB : ViewDataBinding>(
        private val emptyStateTag: String? = null
) : BaseRecyclerFragment() {
    abstract fun onSetup(builder: DslAdaptersInternal.Builder<T, DB>)

    override fun onRecyclerSetup(recycler: RecyclerView) {
        recycler.useUnoxAdapter<T, DB> {
            onSetup(this)
            flowable.showFeedbackWhenEmpty()
        }
    }

    private fun Flowable<List<T>>.showFeedbackWhenEmpty() {
        val emptyStateTag = emptyStateTag ?: return
        binding.stateView.apply {
            observeOn(AndroidSchedulers.mainThread())
                    .subscribe { items ->
                        when (items.isEmpty()) {
                            true -> displayState(emptyStateTag)
                            false -> hideStates()
                        }
                    }.disposeBy(onDestroy)
        }
    }
}