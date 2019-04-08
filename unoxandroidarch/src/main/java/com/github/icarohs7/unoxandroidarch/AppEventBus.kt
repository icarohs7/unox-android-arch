package com.github.icarohs7.unoxandroidarch

import androidx.appcompat.app.AppCompatActivity
import arrow.core.Try
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import timber.log.Timber

/**
 * Event buses used to comunicate with an activity
 * or fragment anywhere on the application
 */
object AppEventBus {
    private val activityOperationsStream: PublishRelay<AppCompatActivity.() -> Unit> = PublishRelay.create()

    /**
     * Object aggregating event emmiters
     */
    object In {

        /**
         * Run an operation within the scope of an activity
         */
        fun enqueueActivityOperation(fn: AppCompatActivity.() -> Unit) {
            activityOperationsStream.accept(fn)
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
        fun subscribeActivity(activity: AppCompatActivity): Unit =
                activity.run {
                    activityOperationsStream
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { action -> Try { action() }.fold(Timber::e) {} }
                            .disposeBy(onDestroy)
                }
    }
}