package com.github.icarohs7.app.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.input.input
import com.github.icarohs7.app.R
import com.github.icarohs7.app.databinding.ActivityMainBinding
import com.github.icarohs7.app.domain.NotificationWorker
import com.github.icarohs7.app.domain.ToastWorker
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxandroidarch.extensions.load
import com.github.icarohs7.unoxandroidarch.extensions.now
import com.github.icarohs7.unoxandroidarch.extensions.startActivity
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseArchActivity
import com.github.icarohs7.unoxandroidarch.scheduling.scheduleOperation
import com.github.icarohs7.unoxandroidarch.toplevel.appHasInternetConnection
import khronos.plus
import khronos.seconds
import kotlinx.coroutines.launch
import splitties.toast.toast
import kotlin.system.measureTimeMillis

@SuppressLint("SetTextI18n")
class MainActivity : BaseArchActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.setRefreshHandler { startActivity<MainActivity>(finishActivity = true) }
        render()
    }

    private fun render() {
        lifecycleScope.launch {
            setupBinding()
        }
    }

    private fun setupBinding(): Unit = with(binding) {
        imgLoading.load(R.drawable.img_placeholder_img_loading)
        setToastNowHandler { toast("Some message here") }
        setToastIn5Handler { scheduleToastIn5() }
        setNotificationIn20Handler { scheduleNotificationIn20() }
        setCancelTasksHandler { cancelTasks() }
        setCheckConnectionHandler { lifecycleScope.launch { showInternetStatus() } }
        setChangeConnectionIpHandler { changeConnectionIp() }
    }

    private fun scheduleToastIn5() {
        MaterialDialog(this).show {
            title(text = "Confirm Toast")
            message(text = "Show toast in 5?")
            negativeButton { }
            positiveButton {
                scheduleOperation<ToastWorker>(now + 5.seconds, "workToDo")
            }
        }
    }

    private fun scheduleNotificationIn20() {
        scheduleOperation<NotificationWorker>(now + 20.seconds, "workToDo")
    }

    private fun cancelTasks() {
        WorkManager.getInstance(this).cancelAllWorkByTag("workToDo")
        toast("Tasks cancelled")
    }

    private suspend fun showInternetStatus() {
        var isConnected = false
        val time = measureTimeMillis { isConnected = appHasInternetConnection() }
        binding.txtHasInternet.text = """
            Connected to internet? $isConnected
            Tested on ip ${UnoxAndroidArch.connectionCheckAddress}
            Time to check: ${time}ms
        """.trimIndent()
    }

    private fun changeConnectionIp() {
        MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            input(prefill = UnoxAndroidArch.connectionCheckAddress) { _, s ->
                UnoxAndroidArch.connectionCheckAddress = "$s"
            }
        }
    }
}
