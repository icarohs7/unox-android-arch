package com.github.icarohs7.unoxandroidarch.extensions

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Returns the drawable from the resource ID, if existant, or
 * null if not
 */
fun @receiver:DrawableRes Int.drawableByResourceId(context: Context): Drawable? {
    return ContextCompat.getDrawable(context, this)
}

/**
 * Returns a color int value from a color resource id
 */
@ColorInt
fun @receiver:ColorRes Int.colorById(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

/**
 * Returns a color drawable from a color resource id,
 * or a drawable for the Black color if the id doesn't exist
 */
fun @receiver:ColorRes Int.colorDrawableByResourceId(context: Context): Drawable {
    return colorById(context).let(::ColorDrawable)
}