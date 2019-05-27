package com.github.icarohs7.unoxandroidarch.extensions

import android.Manifest
import android.view.View
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.github.icarohs7.unoxandroidarch.databinding.DialogYesNoBinding
import com.github.icarohs7.unoxandroidarch.toplevel.hasPermissions
import com.github.icarohs7.unoxandroidarch.toplevel.requestPermissionsInternal

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

/**
 * Request the given list of permissions to the user.
 * List of available permissions at [Manifest.permission]
 * @return Whether all permissions have been given or not
 */
suspend fun Fragment.requestPermissions(vararg permissions: String): Boolean {
    return permissions.all { permission ->
        hasPermissions(permission) ||
                requestPermissionsInternal(requireFragmentManager(),
                        lifecycle, permission)
    }
}