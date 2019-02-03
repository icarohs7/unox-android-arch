package com.github.icarohs7.unoxandroidarch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.icarohs7.unoxandroid.extensions.coroutines.cancelCoroutineScope
import com.github.icarohs7.unoxandroidarch.AppEventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * Fragment containing a coroutine scope,
 * cancelling it and all children coroutines
 * when destroyed
 */
abstract class BaseScopedFragment : Fragment(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppEventBus.Out.subscribeFragment(this)
    }

    override fun onDestroy() {
        cancelCoroutineScope()
        super.onDestroy()
    }
}