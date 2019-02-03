package com.github.icarohs7.unoxandroidarch.extensions

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

/**
 * Define the background tint resource of the View
 */
var View.backgroundTintColorResource: Int
    @Deprecated("No Gether", level = DeprecationLevel.ERROR) get() = throw Exception("No gether for tint color")
    set(value) {
        backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, value))
    }

/**
 * Define the background tint of the View
 */
var View.backgroundTintColor: Int
    @Deprecated("No Gether", level = DeprecationLevel.ERROR) get() = throw Exception("No gether for tint resource")
    set(value) {
        backgroundTintList = ColorStateList.valueOf(value)
    }

/**
 * Resize a view with a new width and height
 */
fun View.resize(w: Int? = null, h: Int? = null) {
    val lp = this.layoutParams
    if (lp != null) {
        lp.width = w ?: lp.width
        lp.height = h ?: lp.height
        this.layoutParams = lp
        this.requestLayout()
        this.invalidate()
    }
}

/**
 * Remove a view from its parent layout
 */
fun View.removeFromParent() {
    val parent: ViewGroup? = this.parent as? ViewGroup?
    parent?.removeView(this)
}

/**
 * Toggles the scale of the view according to the first parameter,
 * when true it will [animateScaleIn], else will [animateScaleOut]
 */
fun View.animateToggleScale(
        show: Boolean,
        duration: Long = 500L,
        callback: ViewPropertyAnimator.() -> Unit = {}
): ViewPropertyAnimator {
    return if (show) {
        this.animateScaleIn(duration, callback)
    } else {
        this.animateScaleOut(duration, callback)
    }
}

/**
 * Animate a view with a scale in animation and a customizable duration
 * and onEnding callback
 */
fun View.animateScaleIn(duration: Long = 500L, callback: ViewPropertyAnimator.() -> Unit = {}): ViewPropertyAnimator {
    return animate().setDuration(duration).doOnEnd(callback).scaleX(1f).scaleY(1f)
}

/**
 * Animate a view with a scale out animation and a customizable duration
 * and onEnding callback
 */
fun View.animateScaleOut(duration: Long = 500L, callback: ViewPropertyAnimator.() -> Unit = {}): ViewPropertyAnimator {
    return animate().setDuration(duration).doOnEnd(callback).scaleX(0f).scaleY(0f)
}

/**
 * Toggles the fading of the view according to the first parameter,
 * when true it will [animateFadeIn], else will [animateFadeOut]
 */
fun View.animateToggleFade(
        show: Boolean,
        duration: Long = 500L,
        callback: ViewPropertyAnimator.() -> Unit = {}
): ViewPropertyAnimator? {
    return if (show) {
        this.animateFadeIn(duration, callback)
    } else {
        this.animateFadeOut(duration, callback)
    }
}

/**
 * Animate a view with a fade in animation and a customizable duration
 * and onEnding callback
 */
fun View.animateFadeIn(duration: Long = 500L, callback: ViewPropertyAnimator.() -> Unit = {}): ViewPropertyAnimator {
    return animate().setDuration(duration).doOnEnd(callback).alpha(1f)
}

/**
 * Animate a view with a fade out animation and a customizable duration
 * and onEnding callback
 */
fun View.animateFadeOut(duration: Long = 500L, callback: ViewPropertyAnimator.() -> Unit = {}): ViewPropertyAnimator {
    return animate().setDuration(duration).doOnEnd(callback).alpha(0f)
}

/** Set the view's visibility to [View.VISIBLE] */
fun View.show() {
    isVisible = true
}

/** Set the view's visibility to [View.GONE], or the parameterized visibility */
fun View.hide(hiddenState: Int = View.GONE) {
    visibility = hiddenState
}