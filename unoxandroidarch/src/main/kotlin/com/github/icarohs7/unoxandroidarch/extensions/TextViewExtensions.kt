package com.github.icarohs7.unoxandroidarch.extensions

import android.text.Editable
import android.text.TextWatcher
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

/**
 * Add a text listener to the given [TextView]
 */
fun TextView.onTextChange(
        beforeChange: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
        afterChange: (s: Editable?) -> Unit = {},
        onChange: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> }
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterChange(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeChange(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onChange(s, start, before, count)
        }
    })
}

/**
 * Add a callback to be called everytime the
 * text of the given [TextView] change
 */
fun TextView.onTextChange(callback: (s: CharSequence) -> Unit) {
    onTextChange(onChange = { text, _, _, _ ->
        callback(text?.toString().orEmpty())
    })
}