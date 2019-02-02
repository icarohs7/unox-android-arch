package com.github.icarohs7.unoxandroidarch.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes

/**
 * Load a image drawable onto a ImageView from a drawable resource
 */
fun ImageView.setImageDrawable(@DrawableRes resource: Int) {
    setImageDrawable(resource.drawableByResourceId(context))
}