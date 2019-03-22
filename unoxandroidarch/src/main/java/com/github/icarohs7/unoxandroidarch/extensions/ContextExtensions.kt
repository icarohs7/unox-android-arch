package com.github.icarohs7.unoxandroidarch.extensions

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import arrow.core.Tuple2
import arrow.core.andThen
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxandroidarch.databinding.DialogYesNoBinding
import com.vanniktech.rxpermission.Permission
import com.vanniktech.rxpermission.RealRxPermission
import kotlinx.coroutines.rx2.await
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.layoutInflater
import java.util.Calendar
import kotlin.reflect.KClass

/**
 * Navigate from an activity to another
 */
fun <T : AppCompatActivity> Context.navigateTo(
        destination: KClass<T>,
        extras: Bundle = bundleOf(),
        finishActivity: Boolean = false
) {
    val intent = Intent(this, destination.java)
    intent.putExtras(extras)
    startActivity(intent)
    if (this is Activity) {
        UnoxAndroidArch.animationType.executeFn(this)
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

/**
 * Return a time picker dialog
 */
fun Context.dialogTimePicker(listener: (hour: Int, minute: Int) -> Unit): TimePickerDialog {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    return TimePickerDialog(
            this,
            { _, h, m -> listener(h, m) },
            hour,
            minute,
            DateFormat.is24HourFormat(this))
}

/** Show a confirm dialog */
fun Context.showConfirmDialog(
        title: String = "",
        message: String = "",
        builder: com.github.icarohs7.unoxandroidarch.databinding.DialogYesNoBinding.(MaterialDialog) -> Unit
) {
    val (binding, dialog) = newConfirmDialog(title, message)
    binding.builder(dialog)
}

/** Show a confirm dialog */
fun Context.showConfirmDialog(
        title: String = "",
        message: String = "",
        yesHandler: View.OnClickListener
) {
    val (binding, dialog) = newConfirmDialog(title, message)
    binding.setYesHandler {
        yesHandler.onClick(it)
        dialog.dismiss()
    }
}

/** Helper used to create an instance of the dialog */
internal fun Context.newConfirmDialog(
        title: String = "",
        message: String = ""
): Tuple2<DialogYesNoBinding, MaterialDialog> {
    val binding = DialogYesNoBinding.inflate(layoutInflater)
    binding.title = title
    binding.message = message
    val dialog = MaterialDialog(this)
            .customView(view = binding.linearLayoutDialogyesno, noVerticalPadding = true)
            .apply { show() }
    binding.setNoHandler { dialog.dismiss() }
    return Tuple2(binding, dialog)
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
 * Return whether the app has all the given permissions allowed
 * or not. List of valid permissions at [Manifest.permission]
 */
fun Context.hasGrantedPermissions(vararg permissions: String): Boolean {
    val allowed = PackageManager.PERMISSION_GRANTED
    val getPermission = { name: String -> ContextCompat.checkSelfPermission(this, name) }
    val isAllowed = { permission: Int -> permission == allowed }

    val hasPermission = getPermission andThen isAllowed
    return permissions.all(hasPermission)
}

/**
 * Request the given list of permissions to the user.
 * List of available permissions at [Manifest.permission]
 * @return Whether all permissions have been given or not
 */
suspend fun Context.requestPermissions(vararg permissions: String): Boolean {
    val hasPermission = { p: Permission -> p.state() == Permission.State.GRANTED }
    return hasGrantedPermissions(*permissions) ||
            RealRxPermission
                    .getInstance(this)
                    .requestEach(*permissions)
                    .all(hasPermission)
                    .await()
}

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