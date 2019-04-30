package com.github.icarohs7.unoxandroidarch.extensions

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Make the upstream execute on [Schedulers.computation]
 * and the downstream on [AndroidSchedulers.mainThread]
 */
fun <T> Flowable<T>.setupAndroidSchedulers(): Flowable<T> {
    return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
}