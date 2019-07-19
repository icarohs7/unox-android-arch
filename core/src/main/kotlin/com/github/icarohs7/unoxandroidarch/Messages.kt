package com.github.icarohs7.unoxandroidarch

import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import br.com.goncalves.pugnotification.notification.Load
import br.com.goncalves.pugnotification.notification.PugNotification
import com.andrognito.flashbar.Flashbar

object Messages {
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