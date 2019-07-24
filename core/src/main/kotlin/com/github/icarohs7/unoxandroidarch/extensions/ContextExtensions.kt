package com.github.icarohs7.unoxandroidarch.extensions

import android.app.Activity
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import splitties.systemservices.activityManager
import java.util.Calendar
import kotlin.reflect.KClass

/**
 * Launch the given activity
 */
inline fun <reified T : AppCompatActivity> Context.startActivity(
        extras: Bundle = bundleOf(),
        finishActivity: Boolean = false
) {
    startActivity(T::class, extras, finishActivity)
}

/**
 * Launch the given activity
 */
fun <T : AppCompatActivity> Context.startActivity(
        destination: KClass<T>,
        extras: Bundle = bundleOf(),
        finishActivity: Boolean = false
) {
    val intent = Intent(this, destination.java)
    intent.putExtras(extras)
    startActivity(intent)
    if (this is Activity) {
        UnoxAndroidArch.screenTransition.executeFn(this)
        if (UnoxAndroidArch.finishActivityOnNavigate || finishActivity) finish()
    }
}

/**
 * Return a date picker dialog
 */
fun Context.dialogDatePicker(listener: (year: Int, month: Int, day: Int) -> Unit): DatePickerDialog {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(
            this,
            { _, y, m, d -> listener(y, m + 1, d) },
            year,
            month,
            day)
}

/** @return A pending intent used to start the given activity */
fun Context.pendingIntentToActivity(activity: KClass<out AppCompatActivity>): PendingIntent? {
    val intent = Intent(this, activity.java)
    return PendingIntent.getActivity(this, 0, intent, 0)
}

/** Helper used to get reference to the activity */
val Activity.context: Context
    get() = this

/**
 * Whether the given service is running
 * or not
 */
@Suppress("DEPRECATION")
fun <T : Any> Context.isServiceRunning(service: KClass<T>): Boolean {
    return (activityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == service.java.name }
}

/**
 * Open the telephone dialer with the
 * given phone number
 */
fun Context.openDialer(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    startActivity(intent)
}