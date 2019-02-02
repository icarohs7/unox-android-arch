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

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.github.icarohs7.unoxandroid.databinding.DialogYesNoBinding
import org.jetbrains.anko.inputMethodManager

/**
 * Dismisses the soft keyboard
 * @param containerId The id of the container within the keyboard is being shown
 */
fun Fragment.hideKeyboard(@IdRes containerId: Int) {
    requireActivity()
            .inputMethodManager
            .hideSoftInputFromWindow(requireActivity().findViewById<View>(containerId)?.windowToken, 0)
}

/**
 * Dismisses the soft keyboard
 * @param container The container within the keyboard is being shown
 */
fun Fragment.hideKeyboard(container: View) {
    requireActivity()
            .inputMethodManager
            .hideSoftInputFromWindow(container.windowToken, 0)
}

/**
 * Get the list of arguments of the [Fragment] and return it as
 * a list of [Pair]
 */
val Fragment.argumentList: List<Pair<String, Any>>
    get() {
        return arguments
                ?.keySet()
                ?.mapNotNull { key ->
                    val value = arguments?.get(key)
                    if (value != null) {
                        key to value
                    } else {
                        null
                    }
                }
                ?: emptyList()
    }

/**
 * Return a [Map] containing the arguments passed to the [Fragment]
 */
val Fragment.argumentMap: Map<String, Any>
    get() = argumentList.toMap()

/**
 * Get the list of arguments of the [Fragment] and return them as
 * a list of [String] pairs
 */
val Fragment.argumentStringList: List<Pair<String, String>>
    get() = argumentList.map { it.first to it.second.toString() }

/**
 * Return a [Map] containing the arguments passed to the [Fragment] as [String]
 */
val Fragment.argumentStringMap: Map<String, String>
    get() = argumentStringList.toMap()

/** Show a confirm dialog */
fun Fragment.showConfirmDialog(
        title: String = "",
        message: String = "",
        builder: DialogYesNoBinding.(MaterialDialog) -> Unit
) {
    val (binding, dialog) = requireContext().newConfirmDialog(title, message)
    binding.builder(dialog)
}

/** Show a confirm dialog */
fun Fragment.showConfirmDialog(
        title: String = "",
        message: String = "",
        yesHandler: View.OnClickListener
) {
    val (binding, dialog) = requireContext().newConfirmDialog(title, message)
    binding.setYesHandler {
        yesHandler.onClick(it)
        dialog.dismiss()
    }
}
