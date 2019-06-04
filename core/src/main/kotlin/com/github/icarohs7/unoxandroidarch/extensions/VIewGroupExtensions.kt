package com.github.icarohs7.unoxandroidarch.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding

/**
 * Set all children of the ViewGroup to Gone
 */
@Suppress("NOTHING_TO_INLINE")
inline fun ViewGroup.hideChildren() {
    this.forEach { child -> child.isGone = true }
}

/**
 * Set all children of the ViewGroup to Gone and
 * the parameterized one to visible
 */
fun ViewGroup.hideChildrenExcept(exceptionChild: View) {
    hideChildren()
    exceptionChild.isVisible = true
}

/**
 * Hides all children of the frame layout and show
 * the parameterized child
 */
@Suppress("NOTHING_TO_INLINE")
inline fun ViewGroup.showChild(child: View) {
    hideChildrenExcept(child)
}

/**
 * Fade and hide all children of the frame layout,
 * then show the parameterized child and fadeIn
 */
fun ViewGroup.fadeInChild(child: View) {
    this.animateFadeOut {
        hideChildrenExcept(child)
        animateFadeIn()
    }
}

/**
 * Scale out and hide all children of the frame layout,
 * then show the parameterized child and ScaleIn
 */
fun ViewGroup.scaleInChild(child: View) {
    this.animateScaleOut {
        hideChildrenExcept(child)
        animateScaleIn()
    }
}

/**
 * Remove all views and add the given
 * view to the viewgroup
 */
fun ViewGroup.replaceAllViews(view: View) {
    removeAllViews()
    addView(view)
}

/**
 * Remove all views and add the given
 * databinding view root to the viewgroup
 */
fun ViewGroup.replaceAllViews(databinding: ViewDataBinding) {
    removeAllViews()
    addView(databinding.root)
}