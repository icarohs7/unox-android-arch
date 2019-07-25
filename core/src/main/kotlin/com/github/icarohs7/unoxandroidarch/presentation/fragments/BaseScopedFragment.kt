package com.github.icarohs7.unoxandroidarch.presentation.fragments

import com.airbnb.mvrx.BaseMvRxFragment
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

/**
 * Fragment containing a coroutine scope,
 * cancelling it and all children coroutines
 * when destroyed
 */
abstract class BaseScopedFragment : BaseMvRxFragment(), CoroutineScope by MainScope() {
    override fun invalidate() {
    }

    /**
     * Launch the collection of the given Flow
     * on the coroutine scope of this component
     */
    fun Flow<*>.launchInScope(): Job = launchIn(this@BaseScopedFragment)

    override fun onDestroy() {
        cancelCoroutineScope()
        super.onDestroy()
    }
}