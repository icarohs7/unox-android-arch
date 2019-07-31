package com.github.icarohs7.unoxandroidarch.extensions.coroutines

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import arrow.core.Tuple2
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Convert the emissions of a broadcast using the
 * given [intentFilter] to emitted items on
 * a [ReceiveChannel], exposing the instance
 * of the receiver and the received intent
 */
fun Context.broadcastReceiverFlow(intentFilter: IntentFilter): Flow<Tuple2<Intent, BroadcastReceiver>> = callbackFlow {
    val receiver = object : BroadcastReceiver() {
        val receiver: BroadcastReceiver = this
        override fun onReceive(context: Context, intent: Intent) {
            offer(Tuple2(intent, receiver))
        }
    }
    registerReceiver(receiver, intentFilter)
    awaitClose { unregisterReceiver(receiver) }
}

/**
 * Suspend until a broadcast matching the given [intentFilter] filter
 * is emmited, then running the block with the received [Intent] and
 * [BroadcastReceiver] instances
 */
suspend fun <T> Context.awaitBroadcast(intentFilter: IntentFilter, block: (Tuple2<Intent, BroadcastReceiver>) -> T): T {
    return suspendCancellableCoroutine { continuation ->
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                unregisterReceiver(this)
                continuation.resume(block(Tuple2(intent, this)))
            }
        }
        registerReceiver(receiver, intentFilter)
        continuation.invokeOnCancellation { unregisterReceiver(receiver) }
    }
}