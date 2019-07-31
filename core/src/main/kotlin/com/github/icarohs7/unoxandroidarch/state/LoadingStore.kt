package com.github.icarohs7.unoxandroidarch.state

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

/**
 * Store used to manage the loading state of the application
 */
object LoadingStore {
    private val stateChannel by lazy { ConflatedBroadcastChannel(false) }
    val flow get() = stateChannel.asFlow()
    val isLoading get() = stateChannel.value

    fun toggleLoading(isLoading: Boolean) {
        stateChannel.offer(isLoading)
    }
}