package com.github.icarohs7.unoxandroidarch.data.db

import io.reactivex.Flowable

/**
 * Embed a mapping transformer to the flowable
 * of a given [BaseDao]
 */
fun <T, R : Any> BaseDao<T>.flowable(transformer: List<T>.() -> R): Flowable<R> {
    return flowable().map(transformer)
}