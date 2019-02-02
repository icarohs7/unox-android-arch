package com.github.icarohs7.app.ui.view

import com.github.icarohs7.app.testutils.BaseActivityTestClass
import com.github.icarohs7.app.testutils.assertNavigationToActivity
import com.github.icarohs7.app.testutils.blockingDelay
import com.github.icarohs7.app.testutils.catchIntents
import org.junit.Test

class TestActivityTimeoutTest : BaseActivityTestClass<TestActivityTimeout>(TestActivityTimeout::class) {
    @Test
    fun should_navigate_after_timeout() {
        catchIntents {
            //Given
            launchAct()
            blockingDelay(3000)
            //When
            blockingDelay(activity.timeout)
            //Then
            assertNavigationToActivity<TestActivity>()
        }
    }
}