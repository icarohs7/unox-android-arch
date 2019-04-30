package com.github.icarohs7.unoxandroidarch.delegates

import androidx.appcompat.app.AppCompatActivity
import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.TestViewModel
import com.github.icarohs7.unoxandroidarch.mockActivity
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
        val (_, act) = mockActivity<AppCompatActivity>()
        //When
        val viewmodel: TestViewModel by act.activityViewModel()
        //Then
        viewmodel.message shouldEqual "NANI!?"
        viewmodel.message = "KONO DIO DA!"
        viewmodel.message shouldEqual "KONO DIO DA!"
    }
}