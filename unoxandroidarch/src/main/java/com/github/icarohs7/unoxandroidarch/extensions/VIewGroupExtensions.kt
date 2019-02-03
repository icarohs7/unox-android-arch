/*
 * MIT License
 *
 * Copyright (c) 2018 Icaro R D Temponi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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