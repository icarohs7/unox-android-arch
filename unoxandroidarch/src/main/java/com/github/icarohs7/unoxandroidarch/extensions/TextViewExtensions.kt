package com.github.icarohs7.unoxandroidarch.extensions

import android.widget.TextView
import androidx.annotation.DrawableRes
import splitties.resources.drawable

/**
 * Define the left drawable of the text view
 */
fun TextView.setDrawableLeft(@DrawableRes drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawable(drawable), null, null, null)
}

/**
 * Define the right drawable of the text view
 */
fun TextView.setDrawableRight(@DrawableRes drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable(drawable), null)
}

/**
 * Define the top drawable of the text view
 */
fun TextView.setDrawableTop(@DrawableRes drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable(drawable), null, null)
}

/**
 * Define the bottom drawable of the text view
 */
fun TextView.setDrawableBottom(@DrawableRes drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, drawable(drawable))
}