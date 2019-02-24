package com.github.icarohs7.unoxandroidarch.state

import androidx.lifecycle.LifecycleOwner
import com.github.icarohs7.unoxandroidarch.Injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import org.koin.standalone.get

/**
 * Add an observer to the loading state of the application
 */
fun LifecycleOwner.addOnLoadingListener(block: (isLoading: Boolean) -> Unit) {
    val stateManager: LoadableState = Injector.get()
    stateManager
            .observable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { block(it.isLoading) }
            .disposeBy(onDestroy)
}