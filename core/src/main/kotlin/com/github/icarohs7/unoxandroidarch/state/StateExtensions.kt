package com.github.icarohs7.unoxandroidarch.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Add an observer to the loading state of the application
 */
fun CoroutineScope.addOnLoadingListener(block: suspend (isLoading: Boolean) -> Unit) {
    LoadingStore
            .flow
            .onEach(block)
            .launchIn(this)
}