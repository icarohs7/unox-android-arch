package com.github.icarohs7.unoxandroidarch.state

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.sellmair.quantum.Quantum
import io.sellmair.quantum.create
import io.sellmair.quantum.rx.rx

/**
 * State manager extended with the capability
 * to have a isLoading state
 */
interface LoadableState : StateManager<LoadingState, Flowable<LoadingState>> {
    /** Toggle the loading state of the application */
    fun toggleLoadingTo(isLoading: Boolean)

    companion object {
        fun create(): LoadableState = LoadableStateImpl()
    }
}

private class LoadableStateImpl : LoadableState {
    private val quantum: Quantum<LoadingState> = Quantum.create(LoadingState())
    override val observable: Flowable<LoadingState> get() = quantum.rx.toFlowable(BackpressureStrategy.LATEST)

    override fun toggleLoadingTo(isLoading: Boolean) {
        quantum.setState { copy(isLoading = isLoading) }
    }

    override fun reduce(reducer: LoadingState.() -> LoadingState) {
        quantum.setState(reducer)
    }

    override fun reduceWithCallback(callback: () -> Unit, reducer: LoadingState.() -> LoadingState) {
        quantum.setStateFuture(reducer).after(callback)
    }

    override fun use(action: LoadingState.() -> Unit) {
        quantum.withState(action)
    }
}