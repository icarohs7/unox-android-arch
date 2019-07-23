package com.github.icarohs7.unoxandroidarch.notification

import android.app.PendingIntent
import android.content.Context
import br.com.goncalves.pugnotification.notification.Load
import br.com.goncalves.pugnotification.notification.PugNotification
import com.github.icarohs7.unoxandroidarch.R

object Notifications {
    /** Build a notification using a fluent api */
    fun Builder(context: Context): Load {
        return PugNotification.with(context).load()
    }

    /** Emit a progress push notification */
    fun progress(
            context: Context,
            title: String,
            message: String,
            progress: Int,
            maxProgress: Int,
            identifier: Int = 9
    ) {
        Builder(context)
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
    fun simple(
            context: Context,
            title: String,
            message: String,
            bigMessage: String? = null,
            autoClose: Boolean = false,
            pendingIntent: PendingIntent? = null,
            identifier: Int = 9,
            builder: Load.() -> Load = { this }
    ) {
        Builder(context)
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
    fun vibrating(
            ctx: Context,
            title: String,
            message: String,
            bigMessage: String? = null,
            activityPendingIntent: PendingIntent? = null,
            identifier: Int = 9,
            builder: Load.() -> Load = { this }
    ) {
        simple(
                context = ctx,
                title = title,
                message = message,
                bigMessage = bigMessage,
                autoClose = true,
                identifier = identifier,
                pendingIntent = activityPendingIntent
        ) {
            flags(android.app.Notification.DEFAULT_ALL)
            builder()
        }
    }
}