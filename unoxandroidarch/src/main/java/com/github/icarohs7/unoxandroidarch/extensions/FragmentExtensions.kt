package com.github.icarohs7.unoxandroidarch.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.github.icarohs7.unoxandroidarch.databinding.DialogYesNoBinding
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
