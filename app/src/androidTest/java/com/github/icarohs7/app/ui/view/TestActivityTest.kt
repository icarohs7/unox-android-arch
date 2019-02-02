package com.github.icarohs7.app.ui.view

import android.view.View
import com.github.icarohs7.app.testutils.BaseActivityTestClass
import com.github.icarohs7.app.testutils.blockingDelay
import com.github.icarohs7.unoxandroid.extensions.coroutines.onForeground
import com.github.icarohs7.unoxandroid.extensions.views.showConfirmDialog
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import se.lovef.assert.v1.shouldBeFalse
import se.lovef.assert.v1.shouldBeTrue
import se.lovef.assert.v1.shouldEqual
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TestActivityTest : BaseActivityTestClass<TestActivity>(TestActivity::class) {
    @Test
    fun coroutine_scope_should_restrain_coroutines_lifetime() {
        //Given
        launchAct()
        //When
        blockingDelay(200)
        val coroutine = activity.launch { delay(5000) }
        rule.finishActivity()
        blockingDelay(800)
        //Then
        coroutine.isCancelled.shouldBeTrue()
    }

    @Test
    fun show_confirm_dialog() {
        runBlocking {
            //Given
            launchAct()
            //When
            val r = async { onForeground { createDialog() } }
            //Then
            blockingDelay(1500)
            assertContains("Testing Confirm Dialog Title")
            assertContains("Testing Confirm Dialog Message")
            assertContains(activity.getString(android.R.string.yes))
            assertContains(activity.getString(android.R.string.no))
            r.isCompleted.shouldBeFalse()
            blockingDelay(2500)

            //When
            clickOn(activity.getString(android.R.string.yes))
            blockingDelay(500)
            r.isCompleted.shouldBeTrue()
            r.await() shouldEqual 1532
            blockingDelay(1500)
        }
    }

    private suspend fun createDialog(): Int {
        return suspendCoroutine { continuation ->
            activity.showConfirmDialog(
                    title = "Testing Confirm Dialog Title",
                    message = "Testing Confirm Dialog Message",
                    yesHandler = View.OnClickListener { continuation.resume(1532) }
            )
        }
    }
}