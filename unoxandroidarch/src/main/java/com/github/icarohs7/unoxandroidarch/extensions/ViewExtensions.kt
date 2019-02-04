package com.github.icarohs7.unoxandroidarch.extensions

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.TextView
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