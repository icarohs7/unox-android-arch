package com.github.icarohs7.unoxandroidarch.delegates

import androidx.fragment.app.Fragment
import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.TestViewModel
import com.github.icarohs7.unoxandroidarch.mockFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class FragViewModelKtTest {
    @Test
    fun `should get view model of fragment from delegate`() {
        //Given
        val (_, _, fragment) = mockFragment<Fragment>()
        //When
        val viewmodel: TestViewModel by fragment.fragViewModel()
        //Then
        viewmodel.message shouldEqual "NANI!?"
        viewmodel.message = "Omai wa mou shindeiru!"
        viewmodel.message shouldEqual "Omai wa mou shindeiru!"
    }
}