package pro.sige.sigecore.ui

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.ui.adapters.showObservable
import com.github.icarohs7.unoxandroidarch.ui.fragments.BaseRecyclerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy

/**
 * Derived class of [BaseRecyclerFragment] observing
 * a RxJava observable, with built-in support for
 * state view to be shown when the data set is empty
 * @param T Type of item shown on the recycler view
 * @param DB Type of databinding of the recycler item
 * @param itemLayout Layout resource of the recycler item
 */
abstract class BaseObservableWatcherRecyclerFragment<T, DB : ViewDataBinding>(
        @LayoutRes private val itemLayout: Int
) : BaseRecyclerFragment() {
    abstract fun getDataSource(): Observable<List<T>>
    abstract fun onBindItem(item: T, view: DB)
    open fun getEmptyStateTag(): String? = null

    override fun onRecyclerSetup(recycler: RecyclerView) {
        recycler.showObservable<T, DB>(itemLayout) {
            observable = getDataSource().apply { showFeedbackWhenEmpty() }
            onItemBind = { item -> onBindItem(item, this) }
        }
    }

    private fun Observable<List<T>>.showFeedbackWhenEmpty() {
        val emptyStateTag = getEmptyStateTag() ?: return
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