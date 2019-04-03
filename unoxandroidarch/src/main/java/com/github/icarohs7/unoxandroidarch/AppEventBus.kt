package com.github.icarohs7.unoxandroidarch

import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseScopedActivity
import com.github.icarohs7.unoxandroidarch.presentation.fragments.BaseScopedFragment
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
    private val activityOperationsStream: PublishRelay<BaseScopedActivity.() -> Unit> = PublishRelay.create()
    private val fragmentOperationsStream: PublishRelay<BaseScopedFragment.() -> Unit> = PublishRelay.create()

    /**
     * Object aggregating event emmiters
     */
    object In {

        /**
         * Run an operation within the scope of an activity
         */
        fun enqueueActivityOperation(fn: BaseScopedActivity.() -> Unit) {
            activityOperationsStream.accept(fn)
        }

        /**
         * Run an operation within the scope of a fragment
         */
        fun enqueueFragmentOperation(fn: BaseScopedFragment.() -> Unit) {
            fragmentOperationsStream.accept(fn)
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
        fun subscribeActivity(activity: BaseScopedActivity): Unit =
                activity.run {
                    activityOperationsStream
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ action -> action() },
                                    { err -> Timber.e(err) })
                            .disposeBy(onDestroy)
                }

        /**
         * Subscribe the given fragmen to the stream of actions
         * being delegated to a fragment on the application
         */
        fun subscribeFragment(fragment: BaseScopedFragment): Unit =
                fragment.run {
                    fragmentOperationsStream
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorReturnItem { Unit }
                            .subscribe({ action -> action() },
                                    { err -> Timber.e(err) })
                            .disposeBy(onDestroy)
                }
    }
}