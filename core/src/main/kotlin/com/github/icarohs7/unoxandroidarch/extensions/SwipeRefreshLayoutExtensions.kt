package com.github.icarohs7.unoxandroidarch.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.icarohs7.unoxcore.extensions.coroutines.onForeground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Execute the given callback when layout
 * is refreshed, automatically stopping
 * the refresh when the callback execution
 * ends
 */
fun SwipeRefreshLayout.onRefresh(callback: () -> Unit) {
    setOnRefreshListener {
        try {
            callback()
        } finally {
            isRefreshing = false
        }
    }
}

/**
 * Execute the given callback when layout
 * is refreshed, automatically stopping
 * the refresh when the callback execution
 * ends
 */
fun SwipeRefreshLayout.onRefresh(scope: CoroutineScope, callback: suspend CoroutineScope.() -> Unit) {
    setOnRefreshListener {
        scope.launch {
            try {
                coroutineScope { callback() }
            } finally {
                onForeground { isRefreshing = false }
            }
        }
    }
}