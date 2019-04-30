package com.github.icarohs7.app.domain

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.icarohs7.app.presentation.MainActivity
import com.github.icarohs7.unoxandroidarch.onActivity
import splitties.toast.toast

class ToastWorker(appContext: Context, params: WorkerParameters) : Worker(appContext, params) {
    override fun doWork(): Result {
        onActivity<MainActivity> { toast("Some message") }
        return Result.success()
    }
}