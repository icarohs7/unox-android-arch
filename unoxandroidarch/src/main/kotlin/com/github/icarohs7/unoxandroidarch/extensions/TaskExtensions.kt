package com.github.icarohs7.unoxandroidarch.extensions

import com.google.android.play.core.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Suspend until the given task return
 */
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        val resumeResult = {
            when (isSuccessful) {
                true -> cont.resume(result)
                false -> cont.resumeWithException(exception)
            }
        }

        if (isComplete) resumeResult()
        else addOnCompleteListener { resumeResult() }
    }
}