package com.github.icarohs7.unoxandroidarch

import android.content.Intent
import android.os.Build
import androidx.core.net.toUri
import androidx.core.text.buildSpannedString
import arrow.core.Try
import com.github.icarohs7.unoxandroidarch.state.LoadingStore
import com.github.icarohs7.unoxandroidarch.testutils.TestActivity
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import com.github.icarohs7.unoxandroidarch.testutils.mockActivity
import com.github.icarohs7.unoxandroidarch.toplevel.Intent
import com.github.icarohs7.unoxandroidarch.toplevel.randomColor
import com.github.icarohs7.unoxandroidarch.toplevel.safeRun
import com.github.icarohs7.unoxandroidarch.toplevel.whileLoading
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.first
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBeFalse
import se.lovef.assert.v1.shouldBeGreaterThan
import se.lovef.assert.v1.shouldBeLessThan
import se.lovef.assert.v1.shouldBeNull
import se.lovef.assert.v1.shouldBeTrue
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class TopLevelKtTest {
    @Before
    fun setUp() {
        startKoin { }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun should_run_operation_while_loading() {
        UnoxAndroidArch.init()

        val c1 = Channel<Int>()
        val c2 = Channel<Int>()

        runBlocking {
            LoadingStore.isLoading.shouldBeFalse()
            val as1 = async { whileLoading { c1.first(); 1000 } }
            delay(300)
            LoadingStore.isLoading.shouldBeTrue()

            c1.send(1)
            as1.await() shouldEqual 1000
            LoadingStore.isLoading.shouldBeFalse()
        }

        runBlocking {
            val as2 = async<Try<Int>> {
                Try {
                    whileLoading {
                        c2.first()
                        throw NumberFormatException("Omai wa mou shindeiru!!")
                    }
                }
            }
            delay(300)
            LoadingStore.isLoading.shouldBeTrue()

            c2.send(1)
            delay(200)
            as2.await()
            LoadingStore.isLoading.shouldBeFalse()
        }
    }

    @Test
    fun should_safely_run_operations() {
        val op1 = safeRun { 10 }
        op1 shouldEqual 10

        val op2 = safeRun<Int> { throw Exception("NANI!?") }
        op2.shouldBeNull()
    }

    @Test
    fun should_build_spanned_string_from_parts() {
        val sp1 = com.github.icarohs7.unoxandroidarch.toplevel.buildSpannedString("Omai ",
                buildSpannedString { append("wa ") }, "mou ", "shindeiru")
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

    @Test
    fun should_generate_random_colors() {
        repeat(1_000_000) {
            val color = randomColor()
            color shouldBeLessThan 1
            color shouldBeGreaterThan -16777217
        }
    }
}