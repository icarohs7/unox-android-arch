package com.github.icarohs7.unoxandroidarch

import android.app.Activity
import android.app.AlertDialog
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.widget.Toast
import br.com.goncalves.pugnotification.notification.Load
import br.com.goncalves.pugnotification.notification.PugNotification
import com.andrognito.flashbar.Flashbar
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import com.github.icarohs7.unoxcore.extensions.coroutines.onForeground
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Messages {
    /**
     * @return An instance of a loading dialog
     */
    fun newLoadingDialog(context: Context, message: String): AlertDialog {
        return SpotsDialog
                .Builder()
                .setContext(context)
                .setMessage(message)
                .build()
    }

    /**
     * Execute an operation, starting a loading dialog before the beginning or after
     * some period of time defined by [timeout] and dismissing it after the operation
     * is done or the [maxTime] in miliseconds has elapsed
     * @return The return value of the [block] invocation
     */
    suspend fun <T> withinLoadingDialog(
            context: Context,
            message: String,
            timeout: Int = 0,
            maxTime: Int = -1,
            block: suspend () -> T
    ): T {
        return onForeground {
            val dialog = newLoadingDialog(context, message)

            val delayedLaunch = CoroutineScope(coroutineContext).launch {
                delay(timeout.toLong())
                dialog.show()
            }

            val delayedMaxTime = CoroutineScope(coroutineContext).launch {
                if (maxTime < 0 || maxTime < timeout) return@launch
                delay(maxTime.toLong())
                dialog.dismiss()
            }

            try {
                onBackground { block() }
            } finally {
                delayedLaunch.cancelAndJoin()
                delayedMaxTime.cancelAndJoin()
                dialog.dismiss()
            }
        }
    }

    /** Show a flashbar snackbar */
    fun flashBar(
            context: Activity,
            message: String,
            duration: Int = 2000,
            gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
            customizer: Flashbar.Builder.() -> Flashbar.Builder = { this }
    ) {
        Flashbar.Builder(context)
                .gravity(gravity)
                .duration(duration.toLong())
                .message(message)
                .messageSizeInSp(16f)
                .customizer()
                .build()
                .show()
    }

    /** Emit a progress push notification */
    fun progressNotification(
            context: Context,
            title: String,
            message: String,
            progress: Int,
            maxProgress: Int,
            identifier: Int = 9
    ) {
        PugNotification.with(context)
                .load()
                .title(title)
                .message(message)
                .identifier(identifier)
                .smallIcon(R.drawable.logo_sm)
                .largeIcon(R.drawable.logo_sm)
                .progress()
                .value(progress, maxProgress, false)
                .build()
    }

    /** Emit a text push notification */
    fun textNotification(
            context: Context,
            title: String,
            message: String,
            bigMessage: String? = null,
            autoClose: Boolean = false,
            pendingIntent: PendingIntent? = null,
            identifier: Int = 9,
            builder: Load.() -> Load = { this }
    ) {
        PugNotification.with(context)
                .load()
                .title(title)
                .message(message)
                .apply { bigMessage?.let { bigTextStyle(it) } }
                .identifier(identifier)
                .smallIcon(R.drawable.logo_sm)
                .largeIcon(R.drawable.logo_sm)
                .autoCancel(autoClose)
                .apply { pendingIntent?.let(this::click) }
                .builder()
                .simple()
                .build()
    }

    /** Show a toast in the given context or delegate it to the activity actions stream */
    fun toast(message: String, duration: Int = Toast.LENGTH_LONG, context: Context? = null) {
        context
                ?.let { Toast.makeText(it, message, duration).show() }
                ?: onActivity { Toast.makeText(this, message, duration).show() }
    }

    /**
     * Emit a notification with vibration and sounds
     * that when clicked is closed and takes the user
     * to the SplashActivity of the application
     */
    fun defaultVibratingNotification(
            ctx: Context,
            title: String,
            message: String,
            bigMessage: String? = null,
            activityPendingIntent: PendingIntent? = null,
            identifier: Int = 9,
            builder: Load.() -> Load = { this }
    ) {
        textNotification(
                context = ctx,
                title = title,
                message = message,
                bigMessage = bigMessage,
                autoClose = true,
                identifier = identifier,
                pendingIntent = activityPendingIntent
        ) {
            flags(Notification.DEFAULT_ALL)
            builder()
        }
    }
}