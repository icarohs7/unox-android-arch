package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxStateStore
import com.airbnb.mvrx.RealMvRxStateStore
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseScopedMvRxViewModel<S : MvRxState>(
        initialState: S,
        debugMode: Boolean = UnoxAndroidArch.isDebug,
        stateStore: MvRxStateStore<S> = RealMvRxStateStore(initialState)
) : BaseMvRxViewModel<S>(initialState, debugMode, stateStore), CoroutineScope by MainScope() {
    override fun onCleared() {
        cancelCoroutineScope()
        super.onCleared()
    }
}