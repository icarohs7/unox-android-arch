/*
 * MIT License
 *
 * Copyright (c) 2018 Icaro R D Temponi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.icarohs7.unoxandroidarch.extensions

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import arrow.core.Tuple2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxandroidarch.databinding.DialogYesNoBinding
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