package com.github.icarohs7.unoxandroidarch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.net.Uri
import android.os.Bundle
import android.text.Spanned
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import arrow.effects.IO
import com.andrognito.flashbar.Flashbar
import com.github.icarohs7.unoxandroidarch.extensions.now
import com.github.icarohs7.unoxandroidarch.state.LoadableState
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import com.github.icarohs7.unoxcore.sideEffectBg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import org.koin.core.get
import splitties.init.appCtx
import splitties.permissions.PermissionRequestResult
import splitties.permissions.hasPermission
import splitties.permissions.requestPermission
import splitties.systemservices.connectivityManager
import splitties.systemservices.locationManager
import timber.log.Timber
import top.defaults.drawabletoolbox.DrawableBuilder
import java.net.InetSocketAddress
import java.net.Socket
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

@Suppress("unused")
private val LoadingDispatcher: ExecutorCoroutineDispatcher by lazy { newSingleThreadContext("loading_worker") }

/** [ViewGroup.LayoutParams.MATCH_PARENT] */
val matchParent
    get() = ViewGroup.LayoutParams.MATCH_PARENT

/** [ViewGroup.LayoutParams.MATCH_PARENT] */
val wrapContent
    get() = ViewGroup.LayoutParams.WRAP_CONTENT

/** Current orientation of the phone */
val appOrientation: Int
    get() = appCtx.resources.configuration.orientation

/** Whether the phone is on landscape orientation or not */
val isOnLandscapeOrientation: Boolean
    get() = appOrientation == Configuration.ORIENTATION_LANDSCAPE

/** [AppEventBus.In.enqueueActivityOperation] */
fun onActivity(action: AppCompatActivity.() -> Unit): Unit =
        AppEventBus.In.enqueueActivityOperation(action)

/** [AppEventBus.In.enqueueActivityOperation] */
@JvmName("onActivityT")
inline fun <reified T : Activity> onActivity(noinline action: T.() -> Unit): Unit =
        AppEventBus.In.enqueueActivityOperation { if (this is T) action() }

/**
 * Return whether the app has all the given permissions allowed
 * or not. List of valid permissions at [Manifest.permission]
 */
fun hasPermissions(vararg permissions: String): Boolean {
    return permissions.all(::hasPermission)
}

/**
 * Request the given [permissions], returning whether
 * all were granted or not
 */
internal suspend fun requestPermissionsInternal(
        fragManager: FragmentManager,
        lifecycle: Lifecycle,
        vararg permissions: String
): Boolean {
    return permissions.all { requestPermission(fragManager, lifecycle, it) == PermissionRequestResult.Granted }
}

/**
 * Helper function used to start loading while a request
 * is made and stop when it's done, running on a single
 * thread background dispatcher by default
 */
suspend fun <T> whileLoading(context: CoroutineContext = LoadingDispatcher, fn: suspend CoroutineScope.() -> T): T {
    return withContext(context) {
        startLoading()
        val result = Try { fn() }
        stopLoading()
        when (result) {
            is Success -> result.value
            is Failure -> throw result.exception
        }
    }
}


/** Check whether the application has connectivity to the internet */
suspend fun appHasInternetConnection(): Boolean = onBackground {
    val adapterOn = connectivityManager.activeNetworkInfo?.isConnected ?: false
    val tryConnSequence = sequence {
        for (i in 0 until 3)
            yield(Try {
                val s = Socket()
                s.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                s.close()
            })
    }

    adapterOn && tryConnSequence.any { it is Success }
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

/**
 * Return the last know location if available or request the
 * current location using the network provider. **Needs permission**
 */
@SuppressLint("MissingPermission")
suspend fun getCurrentLocation(): Try<Location> {
    return Try {
        val locManager = locationManager
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
inline fun <T> safeRun(block: () -> T): T? {
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

/**
 * Build a spanned string from multiple parts
 */
fun buildSpannedString(vararg parts: CharSequence): Spanned {
    return buildSpannedString {
        append(*parts)
    }
}

/**
 * @see [Intent]
 */
@Suppress("FunctionName")
inline fun <reified T> Intent(packageContext: Context): Intent {
    return Intent(packageContext, T::class.java)
}

/**
 * @see [Intent]
 */
@Suppress("FunctionName")
inline fun <reified T> Intent(action: String, uri: Uri, packageContext: Context): Intent {
    return Intent(action, uri, packageContext, T::class.java)
}

/**
 * Create a [ColorStateList] from
 * a color int
 */
@Suppress("FunctionName", "NOTHING_TO_INLINE")
inline fun ColorStateList(@ColorInt color: Int): ColorStateList {
    return ColorStateList.valueOf(color)
}