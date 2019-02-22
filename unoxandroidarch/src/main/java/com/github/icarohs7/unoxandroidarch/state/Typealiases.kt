package com.github.icarohs7.unoxandroidarch.state

typealias SuspendReducer<T> = suspend T.() -> T
typealias Reducer<T> = T.() -> T
typealias SuspendAction<T> = suspend T.() -> Unit
typealias Action<T> = T.() -> Unit