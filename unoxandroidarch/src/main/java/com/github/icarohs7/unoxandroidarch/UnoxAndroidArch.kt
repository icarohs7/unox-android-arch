package com.github.icarohs7.unoxandroidarch

import android.content.Context
import androidx.annotation.AnimRes
import com.github.icarohs7.unoxandroidarch.domain.BungeeAnim
import com.github.icarohs7.unoxandroidarch.state.LoadableState
import com.github.icarohs7.unoxcore.UnoxCore
import com.github.icarohs7.unoxcore.delegates.mutableLazy
import io.hypertrack.smart_scheduler.SmartScheduler
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import spencerstudios.com.bungeelib.Bungee
import splitties.init.appCtx
import timber.log.Timber

object UnoxAndroidArch {
    /**
     * Initialize the library
     */
    fun init() {
        with(UnoxCore) {
            foregroundDispatcher = Dispatchers.Main
            logger = { Timber.tag("UnoxCore").i("$it") }
        }

        loadKoinModules(module {
            single { SmartScheduler.getInstance(appCtx) }
            single { LoadableState.create() }
        })
    }

    /**
     * Whether the app is built on debug mode or not,
     * mainly used for MvRx support
     */
    var isDebug: Boolean by mutableLazy { BuildConfig.DEBUG }

    /**
     * Animation used at the transition between activities
     */
    var screenTransition: AnimationType by mutableLazy { AnimationType.NO_ANIMATION }

    /**
     * When set to true, every call to navigateTo from
     * an activity will also finish it after the navigation
     */
    var finishActivityOnNavigate: Boolean by mutableLazy { false }

    /**
     * Animations available at the [screenTransition]
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