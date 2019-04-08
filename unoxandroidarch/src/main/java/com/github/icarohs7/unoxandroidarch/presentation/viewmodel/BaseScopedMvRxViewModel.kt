package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class BaseScopedMvRxViewModel<S : MvRxState>(
        initialState: S,
        debugMode: Boolean = UnoxAndroidArch.isDebug
) : BaseMvRxViewModel<S>(initialState, debugMode), CoroutineScope by MainScope() {
    override fun onCleared() {
        cancelCoroutineScope()
        super.onCleared()
    }
}