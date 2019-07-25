package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

/**
 * Base viewmodel class with a coroutine scope,
 * cancelling all coroutines when cleared
 */
abstract class BaseScopedViewModel : ViewModel(), CoroutineScope by MainScope() {
    /**
     * Launch the collection of the given Flow
     * on the coroutine scope of this component
     */
    fun Flow<*>.launchInScope(): Job = launchIn(this@BaseScopedViewModel)

    override fun onCleared() {
        cancelCoroutineScope()
        super.onCleared()
    }
}