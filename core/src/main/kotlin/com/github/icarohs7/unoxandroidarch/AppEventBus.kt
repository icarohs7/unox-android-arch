package com.github.icarohs7.unoxandroidarch

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import arrow.core.Try
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * Event buses used to comunicate with an activity
 * or fragment anywhere on the application
 */
object AppEventBus {
    private val activityOpStream = Channel<FragmentActivity.() -> Unit>()
    private val activityOpFlow = activityOpStream.broadcast().asFlow()

    /**
     * Object aggregating event emmiters
     */
    object In {

        /**
         * Run an operation within the scope of an activity
         */
        fun enqueueActivityOperation(fn: FragmentActivity.() -> Unit) {
            activityOpStream.offer(fn)
        }
    }

    /**
     * Object aggregating event output channels
     */
    object Out {
        /**
         * Subscribe the given activity to the stream of actions
         * being delegated to an activity on the application
         */
        fun subscribeActivity(activity: FragmentActivity): Unit = with(activity) {
            activityOpFlow
                    .onEach { action -> Try { action() }.fold(Timber::e) {} }
                    .launchIn(activity.lifecycleScope)
        }
    }
}