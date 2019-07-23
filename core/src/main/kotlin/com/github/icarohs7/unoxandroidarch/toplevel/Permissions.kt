package com.github.icarohs7.unoxandroidarch.toplevel

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import splitties.permissions.PermissionRequestResult
import splitties.permissions.hasPermission
import splitties.permissions.requestPermission

/**
 * Return whether the app has all the given permissions allowed
 * or not. List of valid permissions at [android.Manifest.permission]
 */
fun hasPermissions(vararg permissions: String): Boolean {
    return permissions.all(::hasPermission)
}

/**
 * Request the given [permissions], returning whether
 * all were granted or not
 */
internal suspend fun requestPermissionsInternal(
        fragManager: FragmentManager,
        lifecycle: Lifecycle,
        vararg permissions: String
): Boolean {
    return permissions.all {
        requestPermission(fragManager, lifecycle, it) == PermissionRequestResult.Granted
    }
}