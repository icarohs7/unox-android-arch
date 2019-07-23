package com.github.icarohs7.unoxandroidarch.extensions

import android.Manifest
import androidx.fragment.app.Fragment
import com.github.icarohs7.unoxandroidarch.toplevel.hasPermissions
import com.github.icarohs7.unoxandroidarch.toplevel.requestPermissionsInternal

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