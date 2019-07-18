package com.github.icarohs7.unoxandroidarch.extensions

import android.os.Build
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.contains
import androidx.core.view.isVisible
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
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class ViewGroupExtensionsKtTest {
    @Test
    fun should_hide_children_of_view() {
        val (controller, act) = mockActivity<TestActivity>()
        val layout = LinearLayout(act)
        act.setContentView(layout)
        controller.resume()

        val view1 = View(act)
        val view2 = View(act)
        layout += view1
        layout += view2

        view1.isVisible.shouldBeTrue()
        view2.isVisible.shouldBeTrue()

        layout.hideChildren()
        view1.isVisible.shouldBeFalse()
        view2.isVisible.shouldBeFalse()
    }

    @Test
    fun should_hide_children_of_view_except_given_one() {
        val (controller, act) = mockActivity<TestActivity>()
        val layout = LinearLayout(act)
        act.setContentView(layout)
        controller.resume()

        val view1 = View(act)
        val view2 = View(act)
        layout += view1
        layout += view2

        view1.isVisible.shouldBeTrue()
        view2.isVisible.shouldBeTrue()

        layout.hideChildrenExcept(view2)
        view1.isVisible.shouldBeFalse()
        view2.isVisible.shouldBeTrue()
    }

    @Test
    fun should_show_child_of_view_while_hiding_others() {
        val (controller, act) = mockActivity<TestActivity>()
        val layout = LinearLayout(act)
        act.setContentView(layout)
        controller.resume()

        val view1 = View(act)
        val view2 = View(act)
        layout += view1
        layout += view2

        view1.isVisible.shouldBeTrue()
        view2.isVisible.shouldBeTrue()

        layout.showChild(view1)
        view1.isVisible.shouldBeTrue()
        view2.isVisible.shouldBeFalse()
    }

    @Test
    fun should_replace_all_views_of_layout() {
        val (controller, act) = mockActivity<TestActivity>()
        val layout = LinearLayout(act)
        act.setContentView(layout)
        controller.resume()

        val view1 = View(act)
        val view2 = View(act)
        val view3 = View(act)
        layout += view1
        layout += view2

        layout.contains(view1).shouldBeTrue()
        layout.contains(view2).shouldBeTrue()
        layout.contains(view3).shouldBeFalse()


        layout.replaceAllViews(view3)
        layout.contains(view1).shouldBeFalse()
        layout.contains(view2).shouldBeFalse()
        layout.contains(view3).shouldBeTrue()
    }
}