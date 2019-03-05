package com.github.icarohs7.unoxandroidarch.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.github.icarohs7.unoxandroidarch.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import splitties.resources.drawable

/**
 * Load an image drawable onto an ImageView from a drawable resource
 */
fun ImageView.load(@DrawableRes resource: Int) {
    setImageDrawable(drawable(resource))
}

/**
 * Load an image at the given url to the
 * receiving ImageView
 */
fun ImageView.load(
        url: String,
        @DrawableRes placeholder: Int? = R.drawable.img_placeholder_img_loading,
        @DrawableRes onError: Int? = R.drawable.img_error_img_not_found,
        additionalSetup: RequestCreator.() -> RequestCreator = { this }
) {
    Picasso.get()
            .load(url)
            .apply { placeholder?.let(::placeholder) }
            .apply { onError?.let(::error) }
            .additionalSetup()
            .into(this)
}