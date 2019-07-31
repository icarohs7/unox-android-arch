package com.github.icarohs7.unoxandroidarch.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import arrow.core.Try
import com.github.icarohs7.unoxcore.UnoxCore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

/** Retrieve the value of the livedata or return the fallback if null */
fun <T> LiveData<T>.valueOr(fallback: T): T =
        value ?: fallback

/**
 * Convert the given [LiveData] to [Flow]
 * @param emissionIfNull First emission of the Flow
 *                      if the LiveData starts without
 *                      a value, use null to ignore
 *                      and start emitting only after
 *                      the liveData is assigned for the
 *                      first time
 */
fun <T> LiveData<T>.asFlow(emissionIfNull: T?): Flow<T> {
    return callbackFlow {
        val hasFirstEmission = emissionIfNull != null
        if (hasFirstEmission && value == null) Try { send(emissionIfNull!!) }

        val observer = Observer<T> { offer(it) }
        observeForever(observer)
        awaitClose { removeObserver(observer) }
    }.flowOn(UnoxCore.foregroundDispatcher)
}