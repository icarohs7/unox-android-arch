package com.github.icarohs7.unoxandroidarch.data.entities

import java.io.Serializable

/**
 * Generic tuple of a single value
 */
data class Tuple1<T>(val value: T) : Serializable {
    override fun toString(): String = "($value)"
}