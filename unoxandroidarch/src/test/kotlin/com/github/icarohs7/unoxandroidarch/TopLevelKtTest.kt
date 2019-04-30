package com.github.icarohs7.unoxandroidarch

import android.content.Intent
import androidx.core.net.toUri
import androidx.core.text.buildSpannedString
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBeNull
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class TopLevelKtTest {
    @Test
    fun should_safely_run_operations() {
        val op1 = safeRun { 10 }
        op1 shouldEqual 10

        val op2 = safeRun<Int> { throw Exception("NANI!?") }
        op2.shouldBeNull()
    }

    @Test
    fun should_build_spanned_string_from_parts() {
        val sp1 = buildSpannedString("Omai ", buildSpannedString { append("wa ") }, "mou ", "shindeiru")
        sp1 shouldEqual buildSpannedString { append("Omai wa mou shindeiru") }
    }

    @Test
    fun should_create_intents_using_reified_type() {
        val (_, act) = mockActivity<TestActivity>()
        val i1 = Intent<TestActivity>(act)
        val e1 = Intent(act, TestActivity::class.java)
        i1.action shouldEqual e1.action
        i1.categories shouldEqual e1.categories
        i1.component shouldEqual e1.component
        i1.data shouldEqual e1.data
        i1.extras shouldEqual e1.extras

        val i2 = Intent<TestActivity>("LUL", "txt:hi".toUri(), act)
        val e2 = Intent("LUL", "txt:hi".toUri(), act, TestActivity::class.java)
        i2.action shouldEqual e2.action
        i2.categories shouldEqual e2.categories
        i2.component shouldEqual e2.component
        i2.data shouldEqual e2.data
        i2.extras shouldEqual e2.extras
    }
}