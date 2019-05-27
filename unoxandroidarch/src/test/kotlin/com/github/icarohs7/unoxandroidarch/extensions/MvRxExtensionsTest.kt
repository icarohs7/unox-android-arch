package com.github.icarohs7.unoxandroidarch.extensions

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotBe
import se.lovef.assert.v1.typeIs

class MvRxExtensionsTest {
    @Test
    fun should_map_async_values() {
        val a1: Async<Int> = Uninitialized
        val r1 = a1.map { "$it" }
        r1 shouldEqual Uninitialized

        val a2: Async<Int> = Loading()
        val r2 = a2.map { "$it" }
        r2 shouldEqual Loading()
        r2 shouldNotBe a2

        val a3: Async<Int> = Fail(NumberFormatException())
        val r3 = a3.map { "$it" }
        r3 as Fail
        r3.error typeIs NumberFormatException::class
        r3 shouldNotBe a3

        val a4: Async<Int> = Success(1532)
        val r4 = a4.map { "${it + 2}" }
        r4 as Success
        r4 shouldEqual Success("1534")
        r4 shouldNotBe a4

        val a5: Async<Int> = Success(1532)
        val r5 = a5.map { throw NullPointerException() }
        r5 as Fail
        r5.error typeIs NullPointerException::class
        r5 shouldNotBe a5
    }
}