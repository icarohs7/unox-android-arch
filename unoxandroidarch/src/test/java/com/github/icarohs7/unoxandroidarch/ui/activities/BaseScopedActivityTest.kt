package com.github.icarohs7.unoxandroidarch.ui.activities

import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.newActivityController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBeFalse
import se.lovef.assert.v1.shouldBeTrue

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class BaseScopedActivityTest {
    @Test
    fun `should cancel coroutines when destroyed`() {
        val controller = newActivityController
        val act = controller.create().get()

        val job = act.launch { delay(50000) }
        job.isCancelled.shouldBeFalse()

        controller.start()
        job.isCancelled.shouldBeFalse()

        controller.resume()
        job.isCancelled.shouldBeFalse()

        controller.pause()
        job.isCancelled.shouldBeFalse()

        controller.stop()
        job.isCancelled.shouldBeFalse()

        controller.destroy()
        job.isCancelled.shouldBeTrue()
    }
}