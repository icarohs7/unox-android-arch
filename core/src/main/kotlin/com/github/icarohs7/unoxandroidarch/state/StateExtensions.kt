package com.github.icarohs7.unoxandroidarch.state

import androidx.lifecycle.LifecycleOwner
import com.github.icarohs7.unoxandroidarch.Injector
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import io.sellmair.quantum.Quantum
import io.sellmair.quantum.rx.rx
import org.koin.core.get

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

/**
 * Create a state manager from an instance of [Quantum]
 * and a function returning the observable emitting the
 * state
 */
fun <T, O> StateManager.Companion.fromQuantum(
        quantum: Quantum<T>,
        observableFn: Quantum<T>.() -> O
): StateManager<T, O> {
    return object : StateManager<T, O> {
        override val observable: O get() = observableFn(quantum)

        override fun reduce(reducer: Reducer<T>) {
            quantum.setState(reducer)
        }

        override fun reduceWithCallback(callback: () -> Unit, reducer: Reducer<T>) {
            quantum.setStateFuture(reducer).after(callback)
        }

        override fun use(action: Action<T>) {
            quantum.withState(action)
        }
    }
}

/**
 * Create a state manager from an instance of [Quantum]
 * using a Flowable as the observable type
 */
fun <T> StateManager.Companion.fromQuantum(quantum: Quantum<T>): StateManager<T, Flowable<T>> {
    return object : StateManager<T, Flowable<T>> {
        override val observable: Flowable<T> get() = quantum.rx.toFlowable(BackpressureStrategy.LATEST)

        override fun reduce(reducer: Reducer<T>) {
            quantum.setState(reducer)
        }

        override fun reduceWithCallback(callback: () -> Unit, reducer: Reducer<T>) {
            quantum.setStateFuture(reducer).after(callback)
        }

        override fun use(action: Action<T>) {
            quantum.withState(action)
        }
    }
}