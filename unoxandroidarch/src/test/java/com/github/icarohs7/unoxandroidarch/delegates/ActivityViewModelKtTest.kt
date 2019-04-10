package com.github.icarohs7.unoxandroidarch.delegates

import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.TestViewModel
import com.github.icarohs7.unoxandroidarch.newActivityController
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class ActivityViewModelKtTest {
    @Test
    fun `should get view model of activity from delegate`() {
        //Given
        val controller = newActivityController
        val act = controller.create().get()
        //When
        val viewmodel: TestViewModel by act.activityViewModel()
        //Then
        viewmodel.message shouldEqual "NANI!?"
        viewmodel.message = "KONO DIO DA!"
        viewmodel.message shouldEqual "KONO DIO DA!"
    }
}