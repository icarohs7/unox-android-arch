package com.github.icarohs7.unoxandroidarch.presentation.fragments

import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.testFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class BaseScopedFragmentTest {
    @Test
    fun `should start fragment`() {
        testFragment<Frag>()
    }

    class Frag : BaseScopedFragment()
}