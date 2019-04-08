package com.github.icarohs7.unoxandroidarch

import android.app.Activity
import androidx.fragment.app.Fragment
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseScopedActivity
import com.github.icarohs7.unoxandroidarch.presentation.fragments.BaseScopedFragment
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
    fun `should emit values to activity`() {
        val controller = newActivityController.create()
        val act = controller.get()

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

        var v1 = 0
        act.addOnDestroyObserver { v1 = 1 }
        controller.destroy()
        runBlocking { delay(400) }
        onActivity<TestActivity> { v = this@onActivity }
        v shouldEqual null
        v shouldBe null
        v1 shouldEqual 1
    }

    @Test
    fun `should emit values to fragment`() {
        val frag = TestFragment()
        frag.onCreate(null)

        var v: Fragment? = null
        onFragment<Fragment> { v = this@onFragment }
        v shouldEqual frag
        v shouldBe frag

        onFragment<BaseScopedFragment> { v = null }
        v shouldEqual null
        v shouldBe null

        onFragment<TestFragment> { v = this@onFragment }
        v shouldEqual frag
        v shouldBe frag

        onFragment<Fragment> { v = null }
        v shouldEqual null
        v shouldBe null
    }
}