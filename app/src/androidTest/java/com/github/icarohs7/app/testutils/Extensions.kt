package com.github.icarohs7.app.testutils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Cast the [LiveData] to [androidx.lifecycle.MutableLiveData]
 * and post a value to it, suspending until the value is set,
 * or throwing if the LiveData can't be cast
 */
suspend fun <T> LiveData<T>.unsafeSetAwait(value: T): Unit =
        onUi { (this@unsafeSetAwait as MutableLiveData).value = value }