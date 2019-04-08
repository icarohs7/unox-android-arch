package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.icarohs7.unoxandroidarch.AppEventBus
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
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