package com.github.icarohs7.unoxandroidarch.extensions

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.contains
import androidx.core.view.plusAssign
import com.github.icarohs7.unoxandroidarch.testutils.TestActivity
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import com.github.icarohs7.unoxandroidarch.testutils.mockActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBeFalse
import se.lovef.assert.v1.shouldBeTrue

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class ViewExtensionsKtTest {
    @Test
    fun should_remove_view_from_parent() {
        val (controller, act) = mockActivity<TestActivity>()
        val layout = LinearLayout(act)
        act.setContentView(layout)
        controller.resume()

        val view = View(act)
        layout += view
        layout.contains(view).shouldBeTrue()

        view.removeFromParent()
        layout.contains(view).shouldBeFalse()
    }
}