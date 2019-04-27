package com.github.icarohs7.unoxandroidarch

import android.app.Activity
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseScopedActivity
import com.github.icarohs7.unoxcore.extensions.addOnDestroyObserver
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBe
import se.lovef.assert.v1.shouldEqual
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AppEventBusTest {
    @Test
    fun `should emit values to activity`(): Unit = runBlocking<Unit> {
        val (controller, act) = mockActivity<TestActivity>()
        controller.resume()

        var v: Activity? = null
        onActivity { v = this@onActivity }
        v shouldEqual act
        v shouldBe act

        onActivity<BaseScopedActivity> { v = null }
        v shouldEqual null
        v shouldBe null

        onActivity<TestActivity> { v = this@onActivity }
        v shouldEqual act
        v shouldBe act

        onActivity<Activity> { v = null }
        v shouldEqual null
        v shouldBe null

        val async1 = async {
            suspendCoroutine<Int> { continuation ->
                act.addOnDestroyObserver { continuation.resume(1532) }
            }
        }
        delay(200)

        controller.stop()
        controller.destroy()
        delay(400)

        val async2 = async(start = CoroutineStart.LAZY) {
            suspendCoroutine<Int> { continuation ->
                onActivity<TestActivity> { continuation.resume(42) }
                launch {
                    delay(2000)
                    continuation.resume(17)
                }
            }
        }

        async1.await() shouldEqual 1532
        async2.await() shouldEqual 17
    }
}