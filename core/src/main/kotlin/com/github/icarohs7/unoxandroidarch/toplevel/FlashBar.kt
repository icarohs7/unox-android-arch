package com.github.icarohs7.unoxandroidarch.toplevel

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.andrognito.flashbar.Flashbar
import com.github.icarohs7.unoxandroidarch.R

object FlashBar {
    /** Show a flashbar snackbar */
    fun show(
            context: Activity,
            message: String,
            duration: Int = 2000,
            gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
            customizer: Flashbar.Builder.() -> Flashbar.Builder = { this }
    ) {
        Flashbar.Builder(context)
                .gravity(gravity)
                .duration(duration.toLong())
                .message(message)
                .messageSizeInSp(16f)
                .customizer()
                .build()
                .show()
    }

    /** Show a flashbar snackbar */
    fun show(
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
            show(act, message, duration, gravity) {
                bgColorRes?.let(this::backgroundColor)
                bgDrawableRes?.let(this::backgroundDrawable)
                bgDrawable?.let(this::backgroundDrawable)
                this.builder()
            }
        }
        context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
    }

    /** Show a flashbar snackbar with a green gradient background */
    fun success(
            message: String = "",
            duration: Int = 1500,
            gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
            context: Activity? = null
    ) {
        fun messageBuilder(act: Activity) {
            show(act, message, duration, gravity) {
                backgroundDrawable(R.drawable.bg_gradient_green)
            }
        }
        context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
    }

    /** Show a flashbar snackbar with a green gradient background */
    fun info(
            message: String = "",
            duration: Int = 1500,
            gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
            context: Activity? = null
    ) {
        fun messageBuilder(act: Activity) {
            show(act, message, duration, gravity) {
                backgroundDrawable(R.drawable.bg_gradient_blue)
            }
        }
        context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
    }

    /** Show a flashbar snackbar with a red gradient background */
    fun error(
            message: String = "",
            duration: Int = 1500,
            gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
            context: Activity? = null
    ) {
        fun messageBuilder(act: Activity) {
            show(act, message, duration, gravity) {
                backgroundDrawable(R.drawable.bg_gradient_red)
            }
        }
        context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
    }
}