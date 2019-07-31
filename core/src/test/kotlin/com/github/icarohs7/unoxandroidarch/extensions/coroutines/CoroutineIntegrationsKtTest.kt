package com.github.icarohs7.unoxandroidarch.extensions.coroutines

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.os.bundleOf
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O])
class CoroutineIntegrationsKtTest {
    @Test
    fun should_receive_broadcasts_using_receive_channel(): Unit = runBlockingTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        var expected = "0"
        val c = context.broadcastReceiverFlow(IntentFilter("TEST"))
        val job = c.onEach { result ->
            val intent = result.a
            val name = intent.extras?.get("name")
            val age = intent.extras?.get("age")
            expected = "$name of age $age"
        }.launchIn(this)

        val broadcast = { name: String, age: Int ->
            context.sendBroadcast(Intent("TEST").apply { putExtras(bundleOf(Pair("name", name), Pair("age", age))) })
        }

        broadcast("Icaro", 21)
        expected shouldEqual "Icaro of age 21"

        broadcast("Hugo", 22)
        expected shouldEqual "Hugo of age 22"

        job.cancelAndJoin()
        broadcast("Carlos", 21)
        expected shouldEqual "Hugo of age 22"
    }

    @Test
    fun should_await_broadcast() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        runBlocking {
            val job = async {
                context.awaitBroadcast(IntentFilter("TEST")) { (intent, _) ->
                    val name = intent.extras?.get("name")
                    val age = intent.extras?.get("age")
                    "$name of age $age"
                }
            }
            val i = Intent("TEST").apply { putExtras(bundleOf("name" to "Icaro", "age" to 21)) }
            delay(500)
            context.sendBroadcast(i)

            job.await() shouldEqual "Icaro of age 21"
        }
    }
}