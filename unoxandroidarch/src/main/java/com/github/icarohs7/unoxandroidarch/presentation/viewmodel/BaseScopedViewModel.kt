package com.github.icarohs7.unoxandroidarch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * Base viewmodel class with a coroutine scope,
 * cancelling all coroutines when cleared
 */
abstract class BaseScopedViewModel : ViewModel(), CoroutineScope by MainScope() {
    override fun onCleared() {
        cancelCoroutineScope()
        super.onCleared()
    }
}