package com.github.icarohs7.unoxandroidarch

import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

val newActivityController: ActivityController<TestActivity>
    get() = Robolectric.buildActivity(TestActivity::class.java)