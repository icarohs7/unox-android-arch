package com.github.icarohs7.unoxandroidarch.state

import androidx.lifecycle.LifecycleOwner
import com.github.icarohs7.unoxandroid.extensions.observe
import com.github.icarohs7.unoxandroidarch.Injector
import org.koin.standalone.get

/**
 * Add an observer to the loading state of the application
 */
fun LifecycleOwner.addOnLoadingListener(block: (isLoading: Boolean) -> Unit) {
    val stateManager: LoadableState = Injector.get()
    stateManager.observable.observe(this) { block(it.isLoading) }
}