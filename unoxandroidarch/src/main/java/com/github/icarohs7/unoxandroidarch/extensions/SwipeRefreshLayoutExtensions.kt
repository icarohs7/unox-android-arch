package com.github.icarohs7.unoxandroidarch.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/** Define the callback called when the swipe is refreshed */
fun SwipeRefreshLayout.onRefresh(callback: (SwipeRefreshLayout) -> Unit): Unit =
        setOnRefreshListener { callback(this) }