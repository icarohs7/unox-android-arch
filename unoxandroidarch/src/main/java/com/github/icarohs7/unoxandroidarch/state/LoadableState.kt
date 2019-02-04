package com.github.icarohs7.unoxandroidarch.state

import com.snakydesign.livedataextensions.livedata.NonNullLiveData
import com.snakydesign.livedataextensions.nonNull
import io.sellmair.quantum.Quantum
import io.sellmair.quantum.create
import io.sellmair.quantum.livedata.live
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * State manager extended with the capability
 * to have a isLoading state
 */
interface LoadableState : StateManager<LoadingState, NonNullLiveData<LoadingState>> {
    /** Toggle the loading state of the application */
    fun toggleLoadingTo(isLoading: Boolean)

    companion object {
        fun create(): LoadableState = LoadableStateImpl()
    }
}

private class LoadableStateImpl : LoadableState {
    private val quantum: Quantum<LoadingState> = Quantum.create(LoadingState())
    override val observable: NonNullLiveData<LoadingState> get() = quantum.live.nonNull()

    override fun toggleLoadingTo(isLoading: Boolean) {
        quantum.setState { copy(isLoading = isLoading) }
    }

    override suspend fun lastValue(): LoadingState {
        return observable.value ?: suspendCoroutine { continuation -> quantum.withStateIt { continuation.resume(it) } }
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