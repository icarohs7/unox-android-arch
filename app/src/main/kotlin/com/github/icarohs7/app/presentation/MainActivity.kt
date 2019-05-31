package com.github.icarohs7.app.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.work.WorkManager
import com.github.icarohs7.app.R
import com.github.icarohs7.app.data.db.PersonDao
import com.github.icarohs7.app.data.entities.Person
import com.github.icarohs7.app.databinding.ActivityMainBinding
import com.github.icarohs7.app.domain.NotificationWorker
import com.github.icarohs7.app.domain.ToastWorker
import com.github.icarohs7.unoxandroidarch.Injector
import com.github.icarohs7.unoxandroidarch.extensions.load
import com.github.icarohs7.unoxandroidarch.extensions.now
import com.github.icarohs7.unoxandroidarch.extensions.requestPermissions
import com.github.icarohs7.unoxandroidarch.extensions.startActivity
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseBindingActivity
import com.github.icarohs7.unoxandroidarch.toplevel.appHasInternetConnection
import com.github.icarohs7.unoxandroidarch.toplevel.getCurrentLocation
import com.github.icarohs7.unoxandroidarch.toplevel.scheduleOperation
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import khronos.plus
import khronos.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.inject
import splitties.lifecycle.coroutines.awaitResumed
import splitties.toast.toast
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

@SuppressLint("SetTextI18n")
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    private val personDao: PersonDao by Injector.inject()
    private val timer5 = Flowable.interval(5, TimeUnit.SECONDS)

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        binding.setRefreshHandler { startActivity<MainActivity>(finishActivity = true) }
        render()
    }

    private fun render() {
        launch {
            setupBinding()
            launch { showLocation() }
            launch { showDatabase() }
        }
    }

    private fun setupBinding(): Unit = with(binding) {
        imgLoading.load("https://google.com", onError = R.drawable.img_placeholder_img_loading)
        setToastIn5Handler { scheduleToastIn5() }
        setNotificationIn20Handler { scheduleNotificationIn20() }
        setCancelTasksHandler { cancelTasks() }
        setCheckConnectionHandler { launch { showInternetStatus() } }
    }

    private suspend fun showLocation() {
        lifecycle.awaitResumed()
        requestPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        binding.txtLocation.text = getCurrentLocation().toString()
    }

    private suspend fun showDatabase(): Unit = with(personDao) {
        personDao.flowable().observeOn(AndroidSchedulers.mainThread()).subscribe {
            binding.txtDatabase.text = it.joinToString(System.lineSeparator())
        }.disposeBy(onDestroy)

        onBackground {
            while (true) {
                eraseTable()
                delay(500)
                insert(Person(name = "Icaro"))
                delay(500)
                insert(Person(name = "Hugo"))
                delay(500)
                insert(Person(name = "Carlos"))
                delay(500)
            }
        }
    }

    private suspend fun showInternetStatus() {
        var isConnected = false
        val time = measureTimeMillis { isConnected = appHasInternetConnection() }
        binding.txtHasInternet.text = """
            Connected to internet? $isConnected
            Time to check: ${time}ms
        """.trimIndent()
    }

    private fun scheduleToastIn5() {
        scheduleOperation<ToastWorker>(now + 5.seconds, "workToDo")
    }

    private fun scheduleNotificationIn20() {
        scheduleOperation<NotificationWorker>(now + 20.seconds, "workToDo")
    }

    private fun cancelTasks() {
        WorkManager.getInstance().cancelAllWorkByTag("workToDo")
        toast("Tasks cancelled")
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}
