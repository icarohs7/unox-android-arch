package com.github.icarohs7.unoxandroidarch.extensions

import arrow.core.Try
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized

/**
 * Map the contained value of the given Async,
 * if the [transform] function throws, the exception
 * will be captured and a [Fail] will be returned
 */
inline fun <T, R> Async<T>.map(transform: (T) -> R): Async<R> {
    return when (this) {
        Uninitialized -> Uninitialized
        is Loading -> Loading()
        is Success -> Try { Success(transform(this())) }.fold(::Fail) { Success(it()) }
        is Fail -> Fail(this.error)
    }
}