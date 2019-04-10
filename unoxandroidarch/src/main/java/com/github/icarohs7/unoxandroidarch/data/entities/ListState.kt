package com.github.icarohs7.unoxandroidarch.data.entities

import com.airbnb.mvrx.MvRxState

/**
 * Wrapper class used to turn an
 * arbitrary list into a valid
 * [MvRxState]
 */
data class ListState<T>(val value: List<T> = emptyList()) : MvRxState