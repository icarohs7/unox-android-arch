package com.github.icarohs7.unoxandroidarch.toplevel

import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import com.github.icarohs7.unoxandroidarch.Injector
import com.github.icarohs7.unoxandroidarch.state.LoadableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import org.koin.core.get
import kotlin.coroutines.CoroutineContext

@Suppress("unused")
private val LoadingDispatcher: ExecutorCoroutineDispatcher by lazy {
    newSingleThreadContext("loading_worker")
}

/**
 * Helper function used to start loading while a request
 * is made and stop when it's done, running on a single
 * thread background dispatcher by default
 */
suspend fun <T> whileLoading(context: CoroutineContext = LoadingDispatcher, fn: suspend CoroutineScope.() -> T): T {
    return withContext(context) {
        startLoading()
        val result = Try { fn() }
        stopLoading()
        when (result) {
            is Success -> result.value
            is Failure -> throw result.exception
        }
    }
}

/**
 * Toggle the loading state of the application
 * to true
 */
fun startLoading() {
    val stateManager: LoadableState = Injector.get()
    stateManager.toggleLoadingTo(true)
}

/**
 * Toggle the loading state of the application
 * to false
 */
fun stopLoading() {
    val stateManager: LoadableState = Injector.get()
    stateManager.toggleLoadingTo(false)
}