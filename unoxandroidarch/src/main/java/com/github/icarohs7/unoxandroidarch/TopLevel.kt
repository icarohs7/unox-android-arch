package com.github.icarohs7.unoxandroidarch

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.andrognito.flashbar.Flashbar
import com.github.icarohs7.unoxandroidarch.extensions.now
import com.github.icarohs7.unoxandroidarch.state.LoadableState
import com.github.icarohs7.unoxandroidarch.ui.activities.BaseScopedActivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.hypertrack.smart_scheduler.Job
import io.hypertrack.smart_scheduler.SmartScheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext
import org.koin.standalone.get
import timber.log.Timber
import top.defaults.drawabletoolbox.DrawableBuilder
import java.util.Date

@Suppress("unused")
private val LoadingDispatcher: ExecutorCoroutineDispatcher by lazy { newSingleThreadContext("loading_worker") }

/** [AppEventBus.In.enqueueActivityOperation] */
fun onActivity(action: BaseScopedActivity.() -> Unit): Unit =
        AppEventBus.In.enqueueActivityOperation(action)

/** [AppEventBus.In.enqueueActivityOperation] */
@JvmName("onActivityT")
fun <T : Activity> onActivity(action: T.() -> Unit): Unit =
        AppEventBus.In.enqueueActivityOperation { (this as? T)?.action() }

/** [AppEventBus.In.enqueueFragmentOperation] */
fun <T : Fragment> onFragment(action: T.() -> Unit): Unit =
        AppEventBus.In.enqueueFragmentOperation { (this as? T)?.action() }

/**
 * Helper function used to start loading while a request
 * is made and stop when it's done
 */
suspend fun <T> whileLoading(fn: suspend CoroutineScope.() -> T): T =
        withContext(LoadingDispatcher) {
            val stateManager: LoadableState = Injector.get()

            try {
                stateManager.toggleLoadingTo(true)
                fn()
            } finally {
                stateManager.toggleLoadingTo(false)
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
        @DrawableRes bgDrawable: Drawable? = null,
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