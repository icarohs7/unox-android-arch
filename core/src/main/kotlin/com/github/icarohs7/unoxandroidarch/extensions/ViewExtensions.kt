package com.github.icarohs7.unoxandroidarch.extensions

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.github.icarohs7.unoxandroidarch.toplevel.rippleBackgroundDrawable
import splitties.systemservices.inputMethodManager

/**
 * Show the soft keyboard on the view hierarchy of
 * the given receiver
 */
fun View.showKeyboard() {
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Hide the soft keyboard on the view hierarchy of
 * the given receiver
 */
fun View.hideKeyboard() {
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

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
@Suppress("NOTHING_TO_INLINE")
inline fun View.show() {
    isVisible = true
}

/** Set the view's visibility to [View.GONE], or the parameterized visibility */
@Suppress("NOTHING_TO_INLINE")
inline fun View.hide(hiddenState: Int = View.GONE) {
    visibility = hiddenState
}

/**
 * Location of the view, recommended to use after the view is drawn
 * @return A pair with the location of the view on the screen, X and Y, respectively
 */
val View.location: Pair<Int, Int>
    get() {
        val array = intArrayOf(0, 0)
        getLocationOnScreen(array)
        return array[0] to array[1]
    }

/**
 * Add a ripple background to the view and define
 * its click listener
 */
fun View.rippleOnClick(@ColorInt rippleColor: Int = Color.parseColor("#888888"), listener: (View) -> Unit) {
    background = rippleBackgroundDrawable(rippleColor).build()
    setOnClickListener(listener)
}