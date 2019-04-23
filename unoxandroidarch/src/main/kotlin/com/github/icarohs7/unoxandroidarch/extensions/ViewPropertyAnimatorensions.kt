package com.github.icarohs7.unoxandroidarch.extensions

import android.animation.Animator
import android.view.ViewPropertyAnimator

/**
 * Add a callback to be executed when the animation repeat
 */
fun ViewPropertyAnimator.doOnRepeat(fn: ViewPropertyAnimator.() -> Unit): ViewPropertyAnimator {
    setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            fn()
        }

        override fun onAnimationEnd(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    })
    return this
}

/**
 * Add a callback to be executed at the end of the animation
 */
fun ViewPropertyAnimator.doOnEnd(fn: ViewPropertyAnimator.() -> Unit): ViewPropertyAnimator {
    setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            fn()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    })
    return this
}

/**
 * Add a callback to be executed when the animation is cancelled
 */
fun ViewPropertyAnimator.doOnCancel(fn: ViewPropertyAnimator.() -> Unit): ViewPropertyAnimator {
    setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {
            fn()
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    })
    return this
}

/**
 * Add a callback to be executed when the animation is started
 */
fun ViewPropertyAnimator.doOnStart(fn: ViewPropertyAnimator.() -> Unit): ViewPropertyAnimator {
    setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
            fn()
        }
    })
    return this
}