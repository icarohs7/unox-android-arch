package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.RealMvRxStateStore
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * Base ViewModel implementing a [CoroutineScope]
 * and inheriting from [BaseMvRxViewModel]
 */
open class NxMvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(
        initialState,
        UnoxAndroidArch.isDebug,
        RealMvRxStateStore(initialState)
), CoroutineScope by MainScope() {
    /**
     * Connect the given flowable to the
     * state of the viewmodel, updating
     * the state on each emission
     */
    fun Flowable<S>.connectToState() {
        connectToState { this }
    }

    /**
     * Use the given transformer and for each
     * emission of the given Flowable, transform
     * the emmited item and apply the reducer to
     * evolve the state
     */
    fun <T> Flowable<T>.connectToState(transformer: S.(T) -> S) {
        subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { item -> setState { transformer(this, item) } }
                .disposeOnClear()
    }

    /**
     * Subscribe to the given Flowable and automatically
     * dispose the subscription on [onCleared]
     */
    fun <T> Flowable<T>.observe(onNext: (T) -> Unit) {
        subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext)
                .disposeOnClear()
    }

    override fun onCleared() {
        cancelCoroutineScope()
        super.onCleared()
    }
}