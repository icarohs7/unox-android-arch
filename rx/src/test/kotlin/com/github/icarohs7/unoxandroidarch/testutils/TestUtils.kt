package com.github.icarohs7.unoxandroidarch.testutils

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import arrow.core.Tuple2
import com.github.icarohs7.unoxandroidarch.R
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

inline fun <reified T : AppCompatActivity> mockActivity(): Tuple2<ActivityController<T>, T> {
    val controller = Robolectric.buildActivity(T::class.java)
    val act = controller.get()
    act.setTheme(R.style.Theme_AppCompat)
    controller.create()
    val layout = FrameLayout(act).apply { id = 10 }
    act.setContentView(layout)

    return Tuple2(controller, act)
}