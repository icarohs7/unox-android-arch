package com.github.icarohs7.unoxandroidarch.extensions

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.github.icarohs7.unoxandroidarch.testutils.TestActivity
import com.github.icarohs7.unoxandroidarch.testutils.TestApplication
import com.github.icarohs7.unoxandroidarch.testutils.mockActivity
import com.github.icarohs7.unoxandroidarch.testutils.runAllMainLooperMessages
import com.github.icarohs7.unoxcore.UnoxCore
import com.github.icarohs7.unoxcore.extensions.coroutines.onForeground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class LiveDataExtensionsKtTest {
    @Test
    fun should_return_livedata_value_or_fallback(): Unit = runBlocking<Unit> {
        UnoxCore.foregroundDispatcher = Dispatchers.Main
        val l1 = MutableLiveData<String>()
        l1.value shouldEqual null
        l1.valueOr("Omai wa mou shindeiru!") shouldEqual "Omai wa mou shindeiru!"

        val l2 = MutableLiveData<Int>()
        onForeground { l2.value = 42 }
        l2.valueOr(1532) shouldEqual 42

        val l3 = MutableLiveData<String>()
        onForeground { l3.value = null }
        l3.value shouldEqual null
        l3.valueOr("NANI!?") shouldEqual "NANI!?"
    }

    @Test
    fun should_convert_live_data_to_flow() {
        UnoxCore.foregroundDispatcher = Dispatchers.Main
        val (controller, act) = mockActivity<TestActivity>()
        controller.resume()
        val ld1 = MutableLiveData<String>()

        val f1 = ld1.asFlow("Ho, Mukatte Kuru no Ka?")
        var last = ""
        f1.onEach { last = it }.launchIn(act.lifecycleScope)

        runBlocking {
            ld1.value = "Omai wa mou shindeiru!"
            runAllMainLooperMessages()
            ld1.asFlow(null).first() shouldEqual "Omai wa mou shindeiru!"
        }

        runBlocking {
            ld1.value = "Chikadzukanakya, teme o buchi nomesenainde na"
            runAllMainLooperMessages()
            ld1.asFlow(null).first() shouldEqual "Chikadzukanakya, teme o buchi nomesenainde na"
        }

        //Assert cancellation
        controller.destroy()
        runAllMainLooperMessages()

        ld1.value = "NANI!?"
        runAllMainLooperMessages()

        last shouldEqual "Chikadzukanakya, teme o buchi nomesenainde na"
    }
}