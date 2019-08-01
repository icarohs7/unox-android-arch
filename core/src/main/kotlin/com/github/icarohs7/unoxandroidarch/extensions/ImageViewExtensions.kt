package com.github.icarohs7.unoxandroidarch.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import splitties.resources.drawable

/**
 * Load an image drawable onto an ImageView from a drawable resource
 */
fun ImageView.load(@DrawableRes resource: Int) {
    setImageDrawable(drawable(resource))
}