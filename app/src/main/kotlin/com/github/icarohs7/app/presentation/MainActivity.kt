package com.github.icarohs7.app.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.os.Bundle
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
import com.github.icarohs7.unoxandroidarch.extensions.requestPermissions
import com.github.icarohs7.unoxandroidarch.extensions.startActivity
import com.github.icarohs7.unoxandroidarch.location.getCurrentLocation
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseBindingActivity
import com.github.icarohs7.unoxandroidarch.scheduling.scheduleOperation
import com.github.icarohs7.unoxandroidarch.toplevel.appHasInternetConnection
import khronos.plus
import khronos.seconds
import kotlinx.coroutines.launch
import splitties.toast.toast
import kotlin.system.measureTimeMillis

@SuppressLint("SetTextI18n")
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        binding.setRefreshHandler { startActivity<MainActivity>(finishActivity = true) }
        render()
    }

    private fun render() {
        launch {
            setupBinding()
        }
    }

    private fun setupBinding(): Unit = with(binding) {
        imgLoading.load("https://google.com", onError = R.drawable.img_placeholder_img_loading)
        setGetLocationHandler { launch { showLocation() } }
        setToastIn5Handler { scheduleToastIn5() }
        setNotificationIn20Handler { scheduleNotificationIn20() }
        setCancelTasksHandler { cancelTasks() }
        setCheckConnectionHandler { launch { showInternetStatus() } }
        setChangeConnectionIpHandler { changeConnectionIp() }
    }

    private suspend fun showLocation() {
        requestPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        var loc = ""
        val time = measureTimeMillis { loc = getCurrentLocation().toString() }
        binding.txtLocation.text = """
            Current location is: $loc
            Time to get: ${time}ms
        """.trimIndent()
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

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}
