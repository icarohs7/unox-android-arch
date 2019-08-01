package com.github.icarohs7.unoxandroidarch.imageloading.domain.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.github.icarohs7.unoxandroidarch.imageloading.domain.extensions.load

/**
 * Binding adapter used to load an image
 * into an ImageView on xml
 */
@BindingAdapter("app:url_src")
fun ImageView.loadUrlImage(path: String) {
    load(path)
}