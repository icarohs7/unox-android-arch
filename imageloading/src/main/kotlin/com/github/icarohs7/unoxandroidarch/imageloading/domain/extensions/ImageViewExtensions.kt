package com.github.icarohs7.unoxandroidarch.imageloading.domain.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.extensions.load
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Load an image at the given url to the
 * receiving ImageView
 * @param rethrowExceptions If false, [onErrorRes] will be
 *          loaded on the ImageView if any error happen,
 *          otherwise any exception will be rethrown
 */
fun ImageView.load(
        url: String,
        @DrawableRes placeholderRes: Int? = R.drawable.img_placeholder_img_loading,
        @DrawableRes onErrorRes: Int? = R.drawable.img_error_img_not_found,
        rethrowExceptions: Boolean = false,
        additionalSetup: RequestCreator.() -> RequestCreator = { this }
) {
    try {
        Picasso.get()
                .load(url)
                .apply { placeholderRes?.let(::placeholder) }
                .apply { onErrorRes?.let(::error) }
                .additionalSetup()
                .into(this)
    } catch (e: Exception) {
        when (rethrowExceptions) {
            true -> throw e
            false -> load(onErrorRes ?: R.drawable.img_error_img_not_found)
        }
    }
}