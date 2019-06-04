package com.github.icarohs7.unoxandroidarch.presentation.fragments

import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.databinding.MockViewBinding
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import com.github.icarohs7.unoxandroidarch.testutils.testFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class BaseBindingFragmentTest {
    @Test
    fun `should start fragment`() {
        val fragment = testFragment<Frag>()
        fragment.binding.txtMessage.text.toString() shouldEqual "Hello, World!"
    }

    class Frag : BaseBindingFragment<MockViewBinding>() {
        override fun getLayout(): Int {
            return R.layout.mock_view
        }
    }
}