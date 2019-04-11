package com.github.icarohs7.unoxandroidarch.presentation.fragments

import com.airbnb.mvrx.BaseMvRxFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Fragment containing a coroutine scope,
 * cancelling it and all children coroutines
 * when destroyed
 */
abstract class BaseScopedFragment : BaseMvRxFragment(), CoroutineScope by MainScope() {
    override fun invalidate() {
    }

    override fun onDestroy() {
        this.cancel()
        super.onDestroy()
    }
}