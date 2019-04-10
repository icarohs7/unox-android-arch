package com.github.icarohs7.unoxandroidarch

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import arrow.core.Tuple2
import arrow.core.Tuple3
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController
import kotlin.reflect.full.createInstance

inline fun <reified T : Fragment> mockFragment(): Tuple3<ActivityController<AppCompatActivity>, AppCompatActivity, T> {
    val (controller, act) = mockActivity<AppCompatActivity>()
    val fragment = T::class.createInstance()
    act.supportFragmentManager.transaction { replace(10, fragment) }

    return Tuple3(controller, act, fragment)
}

inline fun <reified T : AppCompatActivity> mockActivity(): Tuple2<ActivityController<T>, T> {
    val controller = Robolectric.buildActivity(T::class.java)
    val act = controller.create().get()
    val layout = FrameLayout(act).apply { id = 10 }
    act.setContentView(layout)

    return Tuple2(controller, act)
}