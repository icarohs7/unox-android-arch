package com.github.icarohs7.unoxandroidarch

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import arrow.core.Try
import arrow.effects.IO
import com.andrognito.flashbar.Flashbar
import com.github.icarohs7.unoxandroidarch.extensions.now
import com.github.icarohs7.unoxandroidarch.state.LoadableState
import com.github.icarohs7.unoxcore.sideEffectBg
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.hypertrack.smart_scheduler.Job
import io.hypertrack.smart_scheduler.SmartScheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext
import org.jetbrains.anko.locationManager
import org.koin.core.get
import timber.log.Timber
import top.defaults.drawabletoolbox.DrawableBuilder
import java.util.Date
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Suppress("unused")
private val LoadingDispatcher: ExecutorCoroutineDispatcher by lazy { newSingleThreadContext("loading_worker") }

/** [AppEventBus.In.enqueueActivityOperation] */
fun onActivity(action: AppCompatActivity.() -> Unit): Unit =
        AppEventBus.In.enqueueActivityOperation(action)

/** [AppEventBus.In.enqueueActivityOperation] */
@JvmName("onActivityT")
inline fun <reified T : Activity> onActivity(noinline action: T.() -> Unit): Unit =
        AppEventBus.In.enqueueActivityOperation { if (this is T) action() }

/**
 * Helper function used to start loading while a request
 * is made and stop when it's done, running on a single
 * thread background dispatcher
 */
suspend fun <T> whileLoading(context: CoroutineContext = LoadingDispatcher, fn: suspend CoroutineScope.() -> T): T =
        withContext(context) {
            try {
                startLoading()
                fn()
            } finally {
                stopLoading()
            }
        }


/** Check whether the application has connectivity to the internet */
suspend fun appHasInternetConnection(): Boolean {
    return ReactiveNetwork
            .checkInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .onErrorReturn { false }
            .await()
}

/**
 * Toggle the loading state of the application
 * to true
 */
fun startLoading() {
    val stateManager: LoadableState = Injector.get()
    stateManager.toggleLoadingTo(true)
}

/**
 * Toggle the loading state of the application
 * to false
 */
fun stopLoading() {
    val stateManager: LoadableState = Injector.get()
    stateManager.toggleLoadingTo(false)
}

/** Show a flashbar snackbar */
fun showFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        @ColorRes bgColorRes: Int? = null,
        @DrawableRes bgDrawableRes: Int? = null,
        bgDrawable: Drawable? = null,
        context: Activity? = null,
        builder: Flashbar.Builder.() -> Flashbar.Builder = { this }
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) {
            bgColorRes?.let(this::backgroundColor)
            bgDrawableRes?.let(this::backgroundDrawable)
            bgDrawable?.let(this::backgroundDrawable)
            this.builder()
        }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/** Show a flashbar snackbar with a green gradient background */
fun showSuccessFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        context: Activity? = null
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) { backgroundDrawable(R.drawable.bg_gradient_green) }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/** Show a flashbar snackbar with a green gradient background */
fun showInfoFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        context: Activity? = null
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) { backgroundDrawable(R.drawable.bg_gradient_blue) }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/** Show a flashbar snackbar with a red gradient background */
fun showErrorFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        context: Activity? = null
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) { backgroundDrawable(R.drawable.bg_gradient_red) }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/**
 * Create a [DrawableBuilder] with a preset ripple
 * effect of the given color
 */
fun rippleBackgroundDrawable(@ColorInt color: Int): DrawableBuilder {
    return DrawableBuilder()
            .ripple()
            .rippleColor(color)
}

/**
 * Schedule the given [operation] to happen on the given [timestamp], assigning the
 * to it the identifier [operationId], used to cancel the operation
 */
fun scheduleOperation(timestamp: Date, operationId: Int, operation: (Context, Job) -> Unit) {
    if (timestamp < now) return
    val interval = timestamp.time - now.time
    val scheduler: SmartScheduler = Injector.get()
    val job = Job.Builder(
            operationId,
            operation,
            Job.Type.JOB_TYPE_ALARM,
            "Job with id $operationId happening"
    ).setIntervalMillis(interval).build()
    Timber.i("Operation scheduled")
    scheduler.addJob(job)
}

/** Cancel a scheduled job with a given id */
fun unscheduleOperation(operationId: Int) {
    Timber.i("Operation cancelled")
    Injector.get<SmartScheduler>().removeJob(operationId)
}

/**
 * Return the last know location if available or request the
 * current location using the network provider. **Needs permission**
 */
@SuppressLint("MissingPermission")
suspend fun getCurrentLocation(context: Context): Try<Location> {
    return Try {
        val locManager = context.locationManager
        val lastGpsLoc: Location? = locManager.getLastKnownLocation(GPS_PROVIDER)
        val lastNetworkLoc: Location? = locManager.getLastKnownLocation(NETWORK_PROVIDER)

        suspend fun getLoc(provider: String): Location {
            return suspendCoroutine { continuation ->
                val locationListener = getLocationListener(continuation)
                locManager.requestSingleUpdate(provider, locationListener, null)
            }
        }

        safeRun { lastGpsLoc }
                ?: safeRun { lastNetworkLoc }
                ?: safeRun { getLoc(GPS_PROVIDER) }
                ?: safeRun { getLoc(NETWORK_PROVIDER) }!!
    }
}

/**
 * Location listener using a coroutine continuation to return
 * the location
 */
private fun getLocationListener(continuation: Continuation<Location>): LocationListener {
    return object : LocationListener {
        override fun onLocationChanged(location: Location) {
            continuation.resume(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {
            continuation.resumeWithException(UnsupportedOperationException("Provider disabled"))
        }
    }
}

/**
 * Returns the result of the given block or
 * null if the execution throws an exception
 */
private inline fun <T> safeRun(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        Timber.tag("UnoxAndroidArch").e(e)
        null
    }
}

/**
 * Invoke the given side effect causing operation
 * and return its result if there's internet connection,
 * otherwise fail and return an [IO.never]
 */
suspend fun <T> connectedSideEffectBg(block: suspend CoroutineScope.() -> T): IO<T> {
    if (!appHasInternetConnection()) return IO.never
    return sideEffectBg(block)
}