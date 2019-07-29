package com.github.icarohs7.unoxandroidarch.rx.domain

import androidx.lifecycle.LifecycleOwner
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import kotlinx.coroutines.rx2.rxMaybe
import kotlinx.coroutines.rx2.rxSingle

/**
 * Execute the given suspend map operation and the
 * upstream on the computation scheduler
 */
fun <T : Any, R : Any> Flowable<T>.suspendMap(transform: suspend (T) -> R): Flowable<R> {
    return flatMapSingle { value ->
        rxSingle {
            transform(value)
        }
    }
}

/**
 * Map the flowable to the mapping of
 * the emitted list using the given
 * transformer
 */
fun <T, R> Flowable<out Iterable<T>>.innerMap(transform: (T) -> R): Flowable<List<R>> {
    return this.map { it.map(transform) }
}

/**
 * Execute the given suspend map operation and the
 * upstream on the computation scheduler
 */
fun <T : Any> Flowable<T>.suspendFilter(predicate: suspend (T) -> Boolean): Flowable<T> {
    return flatMapMaybe { value ->
        rxMaybe {
            if (predicate(value)) value
            else null
        }
    }
}

/**
 * Map the flowable to the filteing of
 * the emitted list using the given
 * predicate
 */
fun <T> Flowable<out Iterable<T>>.innerFilter(predicate: (T) -> Boolean): Flowable<List<T>> {
    return this.map { it.filter(predicate) }
}

/**
 * Add the receiving disposable to the [compositeDisposable]
 * passed as a parameter
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable {
    return this.apply { compositeDisposable.add(this) }
}

/**
 * Helper used to setup the subscription and
 * observation threads
 */
fun <T> Flowable<T>.setupAndroidSchedulers(): Flowable<T> {
    return this
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
}

/**
 * Standard process of subscribing to a flowable
 * subscribing on the computation scheduler,
 * observing on the main thread scheduler and
 * auto disposing the subscription when the
 * given lifecycle reaches the destroyed state
 */
fun <T> Flowable<T>.observe(lifecycle: LifecycleOwner, onNext: (T) -> Unit) {
    this
            .setupAndroidSchedulers()
            .subscribe(onNext)
            .disposeBy(lifecycle.onDestroy)
}

/**
 * Standard process of subscribing to a flowable
 * subscribing on the computation scheduler,
 * observing on the main thread scheduler and
 * auto disposing the subscription when the
 * given lifecycle reaches the destroyed state
 */
fun <T> Flowable<T>.observe(lifecycle: LifecycleOwner, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
    this
            .setupAndroidSchedulers()
            .subscribe(onNext, onError)
            .disposeBy(lifecycle.onDestroy)
}

/**
 * Standard process of subscribing to a flowable
 * subscribing on the computation scheduler,
 * observing on the main thread scheduler and
 * auto disposing the subscription when the
 * given lifecycle reaches the destroyed state
 */
fun <T> Flowable<T>.observe(
        lifecycle: LifecycleOwner,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit
) {
    this
            .setupAndroidSchedulers()
            .subscribe(onNext, onError, onComplete)
            .disposeBy(lifecycle.onDestroy)
}