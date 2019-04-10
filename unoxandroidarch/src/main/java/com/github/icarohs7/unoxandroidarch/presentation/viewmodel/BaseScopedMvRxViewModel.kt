package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.RealMvRxStateStore
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * Base ViewModel implementing a [CoroutineScope]
 * and inheriting from [BaseMvRxViewModel]
 */
abstract class BaseScopedMvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(
        initialState,
        UnoxAndroidArch.isDebug,
        RealMvRxStateStore(initialState)
), CoroutineScope by MainScope() {
    override fun onCleared() {
        cancelCoroutineScope()
        super.onCleared()
    }
}