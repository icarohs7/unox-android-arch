package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import arrow.core.Try
import arrow.core.Tuple2
import arrow.core.getOrElse
import com.github.icarohs7.unoxandroidarch.Messages
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.extensions.awaitAppUpdateInfo
import com.github.icarohs7.unoxandroidarch.extensions.isUpdateAvailable
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
        if (checkAppUpdate) GlobalScope.launch { checkAppUpdates() }
        GlobalScope.launch(Dispatchers.Main) {
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
        Messages.defaultVibratingNotification(
                ctx = this,
                title = str(R.string.there_is_an_update_available),
                message = str(R.string.the_app_can_be_updated).format(str(R.string.app_name)),
                bigMessage = str(R.string.the_app_can_be_updated).format(str(R.string.app_name))
        ) {
            smallIcon(R.drawable.ic_system_update_black_24dp)
                    .largeIcon(R.drawable.ic_system_update_black_24dp)
        }
    }

    /** Called before the timer is started */
    open suspend fun beforeTimeout() {
    }

    /** Called after the timeout is finished */
    abstract fun onTimeout()
}