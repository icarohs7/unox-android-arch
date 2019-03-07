package com.github.icarohs7.unoxandroidarch.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isGone
import androidx.core.view.isVisible

/**
 * Set all children of the ViewGroup to Gone
 */
fun ViewGroup.hideChildren() {
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
fun ViewGroup.showChild(child: View) {
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