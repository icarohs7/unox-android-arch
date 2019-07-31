package com.github.icarohs7.unoxandroidarch

import arrow.core.Try
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseScopedActivity
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
    private val activityOpStream = Channel<BaseScopedActivity.() -> Unit>()
    private val activityOpFlow = activityOpStream.broadcast().asFlow()

    /**
     * Object aggregating event emmiters
     */
    object In {

        /**
         * Run an operation within the scope of an activity
         */
        fun enqueueActivityOperation(fn: BaseScopedActivity.() -> Unit) {
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
        fun subscribeActivity(activity: BaseScopedActivity): Unit = with(activity) {
            activityOpFlow
                    .onEach { action -> Try { action() }.fold(Timber::e) {} }
                    .launchIn(activity)
        }
    }
}