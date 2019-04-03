package com.github.icarohs7.unoxandroidarch

import android.content.Context
import androidx.annotation.AnimRes
import com.github.icarohs7.unoxandroidarch.domain.BungeeAnim
import com.github.icarohs7.unoxandroidarch.state.LoadableState
import com.github.icarohs7.unoxcore.delegates.mutableLazy
import io.hypertrack.smart_scheduler.SmartScheduler
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import spencerstudios.com.bungeelib.Bungee
import splitties.init.appCtx

object UnoxAndroidArch {
    /**
     * Initialize the library
     */
    fun init() {
        loadKoinModules(module {
            single { SmartScheduler.getInstance(appCtx) }
            single { LoadableState.create() }
        })
    }

    /** Define the animation used in the navigation transitions used by library */
    fun setActivityAndFragmentTransitionAnimation(animationType: AnimationType) {
        this.animationType = animationType
        enterAnim = animationType.enterRes
        exitAnim = animationType.exitRes
        popEnterAnim = animationType.enterRes
        popExitAnim = animationType.exitRes
    }

    /**
     * Animation used at the transition between activities
     */
    var animationType: AnimationType by mutableLazy { AnimationType.NO_ANIMATION }

    /* Animations to be used by, for example, fragment transitions */
    var enterAnim: Int by mutableLazy { R.anim.zoom_enter }
    var exitAnim: Int by mutableLazy { R.anim.zoom_exit }
    var popEnterAnim: Int by mutableLazy { R.anim.zoom_enter }
    var popExitAnim: Int by mutableLazy { R.anim.zoom_exit }

    /**
     * When set to true, every call to navigateTo from
     * an activity will also finish it after the navigation
     */
    var finishActivityOnNavigate: Boolean by mutableLazy { false }

    /**
     * Animations available at the [animationType]
     */
    enum class AnimationType(@AnimRes val enterRes: Int, @AnimRes val exitRes: Int, val executeFn: (Context) -> Unit) {
        SPLIT(BungeeAnim.split_enter, BungeeAnim.split_exit, Bungee::split),

        SHRINK(BungeeAnim.shrink_enter, BungeeAnim.shrink_exit, Bungee::shrink),

        CARD(BungeeAnim.card_enter, BungeeAnim.card_exit, Bungee::card),

        INOUT(BungeeAnim.in_out_enter, BungeeAnim.in_out_exit, Bungee::inAndOut),

        SWIPE_LEFT(BungeeAnim.swipe_left_enter, BungeeAnim.swipe_left_exit, Bungee::swipeLeft),

        SWIPE_RIGHT(BungeeAnim.swipe_right_enter, BungeeAnim.swipe_right_exit, Bungee::swipeRight),

        SLIDE_UP(BungeeAnim.slide_up_enter, BungeeAnim.slide_up_exit, Bungee::slideUp),

        SLIDE_DOWN(BungeeAnim.slide_down_enter, BungeeAnim.slide_down_exit, Bungee::slideDown),

        SLIDE_LEFT(BungeeAnim.slide_left_enter, BungeeAnim.slide_left_exit, Bungee::slideLeft),

        SLIDE_RIGHT(BungeeAnim.slide_in_left, BungeeAnim.slide_out_right, Bungee::slideRight),

        FADE(BungeeAnim.fade_enter, BungeeAnim.fade_exit, Bungee::fade),

        ZOOM(BungeeAnim.zoom_enter, BungeeAnim.zoom_exit, Bungee::zoom),

        WINDMILL(BungeeAnim.windmill_enter, BungeeAnim.windmill_exit, Bungee::windmill),

        SPIN(BungeeAnim.spin_enter, BungeeAnim.spin_exit, Bungee::spin),

        DIAGONAL(BungeeAnim.diagonal_right_enter, BungeeAnim.diagonal_right_exit, Bungee::diagonal),

        NO_ANIMATION(0, 0, {});
    }
}