package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
import android.view.WindowManager
import com.airbnb.mvrx.BaseMvRxActivity
import com.github.icarohs7.unoxandroidarch.AppEventBus
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

/**
 * Activity containing a coroutine scope,
 * cancelling it and all children coroutines
 * when destroyed
 */
abstract class BaseScopedActivity : BaseMvRxActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(onSetSoftInputMode())
        AppEventBus.Out.subscribeActivity(this)
    }

    /**
     * Define how the window will behave when the soft
     * keyboard is open, defaulting to
     * [WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN]
     */
    open fun onSetSoftInputMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    /**
     * Launch the collection of the given Flow
     * on the coroutine scope of this component
     */
    fun Flow<*>.launchInScope(): Job = launchIn(this@BaseScopedActivity)

    override fun onDestroy() {
        cancelCoroutineScope()
        super.onDestroy()
    }
}