package com.github.icarohs7.app.domain

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.icarohs7.unoxandroidarch.Messages
import com.github.icarohs7.unoxandroidarch.toplevel.onActivity

class NotificationWorker(appContext: Context, params: WorkerParameters) : Worker(appContext, params) {
    override fun doWork(): Result {
        onActivity {
            Messages.textNotification(this, "Some title", "Some message")
        }
        return Result.success()
    }
}