package com.github.icarohs7.unoxandroidarch.extensions

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBe
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class ContextExtensionsKtTest {
    @Test
    fun `should use context as syntactic sugar to current activity`() {
        val act = Robolectric.buildActivity(AppCompatActivity::class.java).create().get()

        act.context shouldEqual act
        act.context shouldBe act
    }
}