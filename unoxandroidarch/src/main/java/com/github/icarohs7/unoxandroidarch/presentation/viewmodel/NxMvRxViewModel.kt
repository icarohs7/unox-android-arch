package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.RealMvRxStateStore
import com.airbnb.mvrx.Success
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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

    /**
     * Helper to map a suspend function to an Async property on the state object.
     */
    fun <T : Any> execute(
            block: suspend () -> T,
            dispatcher: CoroutineDispatcher = Dispatchers.Default,
            reducer: S.(Async<T>) -> S
    ) {
        launch(this.coroutineContext + dispatcher) {
            setState { reducer(Loading()) }
            try {
                val result = block()
                setState { reducer(Success(result)) }
            } catch (e: Exception) {
                setState { reducer(Fail(e)) }
            }
        }
    }

    override fun onCleared() {
        cancelCoroutineScope()
        super.onCleared()
    }
}