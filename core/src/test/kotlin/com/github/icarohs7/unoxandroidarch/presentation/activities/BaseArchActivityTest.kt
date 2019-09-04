package com.github.icarohs7.unoxandroidarch.presentation.activities

import android.os.Build
import com.github.icarohs7.unoxandroidarch.testutils.TestActivity
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import com.github.icarohs7.unoxandroidarch.testutils.mockActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class BaseArchActivityTest {
    @Test
    fun `should start activity`() {
        val (controller, _) = mockActivity<TestActivity>()
        controller.start()
        controller.resume()
        controller.pause()
        controller.stop()
        controller.destroy()
    }
}