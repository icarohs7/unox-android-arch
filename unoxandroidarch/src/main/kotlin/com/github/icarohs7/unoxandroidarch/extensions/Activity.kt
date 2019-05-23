package com.github.icarohs7.unoxandroidarch.extensions

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.github.icarohs7.unoxandroidarch.toplevel.hasPermissions
import com.github.icarohs7.unoxandroidarch.toplevel.requestPermissionsInternal

/** Invoke the given [observer] whenever a new fragment is attached to the supportFragmentManager */
fun FragmentActivity.onFragmentAttached(observer: (Fragment) -> Unit) {
    supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            observer(f)
        }
    }, true)
}

/**
 * Request the given list of permissions to the user.
 * List of available permissions at [Manifest.permission]
 * @return Whether all permissions have been given or not
 */
suspend fun FragmentActivity.requestPermissions(vararg permissions: String): Boolean {
    return permissions.all { permission ->
        hasPermissions(permission) ||
                requestPermissionsInternal(supportFragmentManager,
                        lifecycle, permission)
    }
}