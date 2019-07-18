package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Build
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import com.github.icarohs7.unoxandroidarch.testutils.testFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class BaseScopedFragmentTest {
    @Test
    fun `should start fragment`() {
        testFragment<Frag>()
    }

    class Frag : BaseScopedFragment()
}