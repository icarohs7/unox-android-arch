package com.github.icarohs7.unoxandroidarch

import android.app.Activity
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseScopedActivity
import com.github.icarohs7.unoxcore.extensions.addOnDestroyObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBe
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AppEventBusTest {
    @Test
    fun `should emit values to activity`(): Unit = runBlocking<Unit> {
        val (controller, act) = mockActivity<TestActivity>()
        controller.resume()

        var v: Activity? = null
        onActivity { v = this@onActivity }
        delay(400)
        v shouldEqual act
        v shouldBe act

        onActivity<BaseScopedActivity> { v = null }
        delay(400)
        v shouldEqual null
        v shouldBe null

        onActivity<TestActivity> { v = this@onActivity }
        delay(400)
        v shouldEqual act
        v shouldBe act

        onActivity<Activity> { v = null }
        delay(400)
        v shouldEqual null
        v shouldBe null

        var v1 = 0
        act.addOnDestroyObserver { v1 = 1 }
        controller.destroy()
        delay(800)
        onActivity<TestActivity> { v = this@onActivity }
        v shouldEqual null
        v shouldBe null
        v1 shouldEqual 1
    }
}