package com.github.icarohs7.unoxandroidarch.toplevel

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.andrognito.flashbar.Flashbar
import com.github.icarohs7.unoxandroidarch.Messages
import com.github.icarohs7.unoxandroidarch.R

/** Show a flashbar snackbar */
fun showFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        @ColorRes bgColorRes: Int? = null,
        @DrawableRes bgDrawableRes: Int? = null,
        bgDrawable: Drawable? = null,
        context: Activity? = null,
        builder: Flashbar.Builder.() -> Flashbar.Builder = { this }
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) {
            bgColorRes?.let(this::backgroundColor)
            bgDrawableRes?.let(this::backgroundDrawable)
            bgDrawable?.let(this::backgroundDrawable)
            this.builder()
        }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/** Show a flashbar snackbar with a green gradient background */
fun showSuccessFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        context: Activity? = null
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) {
            backgroundDrawable(R.drawable.bg_gradient_green)
        }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/** Show a flashbar snackbar with a green gradient background */
fun showInfoFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        context: Activity? = null
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) {
            backgroundDrawable(R.drawable.bg_gradient_blue)
        }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/** Show a flashbar snackbar with a red gradient background */
fun showErrorFlashBar(
        message: String = "",
        duration: Int = 1500,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
        context: Activity? = null
) {
    fun messageBuilder(act: Activity) {
        Messages.flashBar(act, message, duration, gravity) {
            backgroundDrawable(R.drawable.bg_gradient_red)
        }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}