package com.github.icarohs7.unoxandroidarch.toplevel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.text.Spanned
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import arrow.core.Try
import arrow.core.getOrElse
import com.github.icarohs7.unoxandroidarch.AppEventBus
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import com.github.icarohs7.unoxcore.extensions.toIntOr
import com.github.icarohs7.unoxcore.extensions.valueOr
import splitties.init.appCtx
import splitties.systemservices.connectivityManager
import timber.log.Timber
import java.net.InetSocketAddress
import java.net.Socket

/** [ViewGroup.LayoutParams.MATCH_PARENT] */
val matchParent
    get() = ViewGroup.LayoutParams.MATCH_PARENT

/** [ViewGroup.LayoutParams.MATCH_PARENT] */
val wrapContent
    get() = ViewGroup.LayoutParams.WRAP_CONTENT

/**
 * Current orientation of the phone
 * @see Configuration.ORIENTATION_LANDSCAPE
 * @see Configuration.ORIENTATION_PORTRAIT
 */
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


/** Check whether the application has connectivity to the internet */
suspend fun appHasInternetConnection(): Boolean = onBackground {
    connectivityManager.activeNetworkInfo
            ?.isConnected
            .valueOr(false)
            .also { adapterOn -> if (!adapterOn) return@onBackground false }

    val checkSequence = sequence {
        val address = UnoxAndroidArch.connectionCheckAddress.split(":")
        val ip = address[0]
        val port = address.getOrElse(1) { "80" }
        val host = InetSocketAddress(ip, port.toIntOr(80))

        while (true) {
            yield(Socket().use { s ->
                Try {
                    s.connect(host, 500)
                    true
                }.getOrElse { false }
            })
        }
    }

    checkSequence.take(5).any { it }
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
 * @return A randomly generated color
 */
@ColorInt
fun randomColor(): Int {
    val nextInt = { (0..255).random() }
    return Color.rgb(nextInt(), nextInt(), nextInt())
}