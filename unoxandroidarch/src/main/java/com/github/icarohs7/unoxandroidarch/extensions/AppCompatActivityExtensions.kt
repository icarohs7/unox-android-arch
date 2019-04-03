package com.github.icarohs7.unoxandroidarch.extensions

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.transaction
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import org.jetbrains.anko.inputMethodManager

/**
 * Execute a fragment transaction with an animation, defined by
 * the settings on the [UnoxAndroidArch] object
 */
fun AppCompatActivity.fragmentTransactionAnimated(fn: FragmentTransaction.() -> Unit) {
    supportFragmentManager?.transaction {
        setCustomAnimations(
                UnoxAndroidArch.enterAnim,
                UnoxAndroidArch.exitAnim,
                UnoxAndroidArch.popEnterAnim,
                UnoxAndroidArch.popExitAnim)
        fn()
    }
}

/**
 * Dismiss the soft keyboard
 * @param containerId The id of the container within the keyboard is being shown
 */
fun AppCompatActivity.hideKeyboard(@IdRes containerId: Int) {
    inputMethodManager.hideSoftInputFromWindow(findViewById<View>(containerId)?.windowToken, 0)
}


/**
 * Dismiss the soft keyboard
 * @param container The container within the keyboard is being shown
 */
fun AppCompatActivity.hideKeyboard(container: View) {
    inputMethodManager.hideSoftInputFromWindow(container.windowToken, 0)
}

/** Invoke the given [observer] whenever a new fragment is attached to the supportFragmentManager */
fun AppCompatActivity.onFragmentAttached(observer: (Fragment) -> Unit) {
    supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            observer(f)
        }
    }, true)
}