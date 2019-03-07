package com.github.icarohs7.unoxandroidarch.state

typealias Reducer<T> = T.() -> T
typealias Action<T> = T.() -> Unit