package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
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
        AppEventBus.Out.subscribeActivity(this)
    }

    override fun onDestroy() {
        cancelCoroutineScope()
        super.onDestroy()
    }
}