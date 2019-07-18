package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Build
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import com.github.icarohs7.unoxandroidarch.testutils.TestBaseTimeoutActivity
import com.github.icarohs7.unoxandroidarch.testutils.mockActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class BaseTimeoutActivityTest {
    @Test
    fun should_launch_activity() {
        val (controller, activity) = mockActivity<TestBaseTimeoutActivity>()
        controller.start()
        runBlocking { delay(600) }
        controller.resume()
        runBlocking { delay(600) }
        controller.pause()
        runBlocking { delay(200) }
        controller.stop()
        runBlocking { delay(200) }
        activity.onTimeout()
        activity.someVariable shouldEqual 1532
        controller.destroy()
        runBlocking { delay(200) }
    }
}