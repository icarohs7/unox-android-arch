package com.github.icarohs7.unoxandroidarch.scheduling

import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.github.icarohs7.unoxandroidarch.extensions.now
import timber.log.Timber
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * Schedule the worker defined by the given type [T]
 * to happen on the given [timestamp]
 *
 * @return The id of the operation request
 */
inline fun <reified T : ListenableWorker> scheduleOperation(timestamp: Date, tag: String? = null): UUID? {
    return scheduleOperation(T::class, timestamp, tag)
}

/**
 * Schedule the worker defined by the given [workerClass]
 * to happen on the given [timestamp]
 *
 * @return The id of the operation request
 */
fun scheduleOperation(
        workerClass: KClass<out ListenableWorker>,
        timestamp: Date,
        tag: String? = null
): UUID? {
    if (timestamp < now) return null

    val interval = timestamp.time - now.time
    val request = OneTimeWorkRequest.Builder(workerClass.java)
            .setInitialDelay(interval, TimeUnit.MILLISECONDS)
            .apply { tag?.let(::addTag) }
            .build()
    WorkManager.getInstance().enqueue(request)

    Timber.i("Operation scheduled")
    return request.id
}