package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.github.icarohs7.unoxandroid.extensions.coroutines.cancelCoroutineScope
import com.github.icarohs7.unoxandroidarch.AppEventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * Activity containing a coroutine scope,
 * cancelling it and all children coroutines
 * when destroyed
 */
abstract class BaseScopedActivity : AppCompatActivity(), CoroutineScope by MainScope() {
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

    override fun onDestroy() {
        cancelCoroutineScope()
        super.onDestroy()
    }
}