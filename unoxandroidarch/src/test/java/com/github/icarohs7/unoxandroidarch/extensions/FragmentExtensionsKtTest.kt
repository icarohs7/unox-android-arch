package com.github.icarohs7.unoxandroidarch.extensions

import androidx.core.os.bundleOf
import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.TestFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class FragmentExtensionsKtTest {
    @Test
    fun `should get fragment's arguments in various ways`() {
        val frag = TestFragment()
        frag.arguments = bundleOf("name" to "Icaro", "age" to 21)

        frag.argumentList.toSet() shouldEqual setOf(Pair("name", "Icaro"), Pair("age", 21))

        frag.argumentMap shouldEqual mapOf(Pair("name", "Icaro"), Pair("age", 21))

        frag.argumentStringList.toSet() shouldEqual setOf(Pair("name", "Icaro"), Pair("age", "21"))

        frag.argumentStringMap shouldEqual mapOf(Pair("name", "Icaro"), Pair("age", "21"))
    }
}