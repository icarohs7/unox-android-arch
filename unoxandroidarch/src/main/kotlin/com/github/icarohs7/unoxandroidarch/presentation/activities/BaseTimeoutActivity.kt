package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.github.icarohs7.unoxandroidarch.Messages
import com.github.icarohs7.unoxandroidarch.R
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import splitties.resources.str
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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
            val delayAsync = launch { delay(timeout.toLong()) }
            if (checkAppUpdate) checkAppUpdates()
            delayAsync.join()
            onTimeout()
        }
    }

    open suspend fun checkAppUpdates() {
        val manager = AppUpdateManagerFactory.create(this)
        val info = manager.appUpdateInfo.await()
        if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
            onAppHasUpdate(info)
    }

    open fun onAppHasUpdate(appUpdateInfo: AppUpdateInfo) {
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

    protected suspend fun <T> Task<T>.await(): T {
        return suspendCancellableCoroutine { cont ->
            addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> cont.resume(task.result)
                    false -> cont.resumeWithException(task.exception)
                }
            }
        }
    }

    /** Called after the timeout is finished */
    abstract fun onTimeout()
}