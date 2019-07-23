package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import arrow.core.Try
import arrow.core.Tuple2
import arrow.core.getOrElse
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.extensions.awaitAppUpdateInfo
import com.github.icarohs7.unoxandroidarch.extensions.isUpdateAvailable
import com.github.icarohs7.unoxcore.tryBg
import com.google.android.play.core.appupdate.AppUpdateInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.resources.str
import timber.log.Timber

/**
 * Base activity derivated from [BaseBindingActivity]
 * with an embedded timeout and a hook called when it's
 * finished
 */
abstract class BaseTimeoutActivity<DB : ViewDataBinding>(
        val timeout: Int = 2000,
        private val checkAppUpdate: Boolean = false
) : BaseBindingActivity<DB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            tryBg { checkAppUpdates() }
            beforeTimeout()
            delay(timeout.toLong())
            onTimeout()
        }
    }

    open suspend fun checkAppUpdates() {
        val (hasUpdate, appUpdateInfo) = Try {
            val (_, info) = awaitAppUpdateInfo()
            val hasUpdate = isUpdateAvailable(info)
            Timber.tag("UnoxAndroidArch").i("App ${str(R.string.app_name)} has update? $hasUpdate")
            Timber.tag("UnoxAndroidArch").i("App ${str(R.string.app_name)} update info? $info")
            Tuple2(hasUpdate, info)
        }.getOrElse { Tuple2(false, null) }

        if (hasUpdate) onAppHasUpdate(appUpdateInfo ?: return)
    }

    open suspend fun onAppHasUpdate(appUpdateInfo: AppUpdateInfo) {
    }

    /** Called before the timer is started */
    open suspend fun beforeTimeout() {
    }

    /** Called after the timeout is finished */
    abstract fun onTimeout()
}