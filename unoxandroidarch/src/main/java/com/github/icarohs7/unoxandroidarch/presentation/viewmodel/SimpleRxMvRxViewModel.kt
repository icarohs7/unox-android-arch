package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import com.airbnb.mvrx.MvRxState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * Simple implementation of [BaseScopedMvRxViewModel]
 * that auto subscribes to the given [stateStream] when
 * it is changed
 */
open class SimpleRxMvRxViewModel<S : MvRxState>(initialState: S) : BaseScopedMvRxViewModel<S>(initialState) {
    private val stateStreamDisposable = CompositeDisposable()

    /**
     * Stream emitting states to the viewmodel
     */
    var stateStream: Flowable<S> = Flowable.empty()
        set(value) {
            field = value
            stateStreamDisposable.clear()
            value.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { setState { it } }
                    .addTo(stateStreamDisposable)
        }

    override fun onCleared() {
        stateStreamDisposable.clear()
        super.onCleared()
    }
}