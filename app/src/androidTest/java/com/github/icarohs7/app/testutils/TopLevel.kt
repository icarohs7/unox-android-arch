package com.github.icarohs7.app.testutils

import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/** Enqueue an action to be executed on the ui and suspend until it's executed */
suspend inline fun <T> onUi(noinline fn: suspend CoroutineScope.() -> T): T =
        withContext(Dispatchers.Main, fn)

/** Verify whether a intent to the given activity was started */
inline fun <reified T : AppCompatActivity> assertNavigationToActivity() {
    Intents.intended(IntentMatchers.hasComponent(T::class.java.name))
}

/** Verify whether a intent to the given activity was started */
fun assertExtrasOnIntent(vararg extra: Pair<String, Any>) {
    extra.forEach { Intents.intended(IntentMatchers.hasExtra(it.first, it.second)) }
}

/** Start recording intents for espresso and stop after the block execution */
inline fun catchIntents(block: () -> Unit) {
    Intents.init()
    try {
        block()
    } finally {
        Intents.release()
    }
}

/** Block the thread for a given amount of miliseconds */
fun blockingDelay(delay: Int) {
    runBlocking { kotlinx.coroutines.delay(delay.toLong()) }
}