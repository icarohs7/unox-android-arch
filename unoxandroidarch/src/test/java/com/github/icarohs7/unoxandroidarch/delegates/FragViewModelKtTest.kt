package com.github.icarohs7.unoxandroidarch.delegates

import android.widget.FrameLayout
import androidx.fragment.app.transaction
import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.TestFragment
import com.github.icarohs7.unoxandroidarch.TestViewModel
import com.github.icarohs7.unoxandroidarch.newActivityController
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
        val controller = newActivityController
        val act = controller.create().get()
        val layout = FrameLayout(act).apply { id = 10 }
        act.setContentView(layout)
        val fragment = TestFragment()
        act.supportFragmentManager.transaction { replace(layout.id, fragment) }
        //When
        val viewmodel: TestViewModel by fragment.fragViewModel()
        //Then
        viewmodel.message shouldEqual "NANI!?"
        viewmodel.message = "Omai wa mou shindeiru!"
        viewmodel.message shouldEqual "Omai wa mou shindeiru!"
    }
}