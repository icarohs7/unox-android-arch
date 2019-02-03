package com.github.icarohs7.unoxandroidarch

import org.robolectric.Robolectric

val newActivityController get() = Robolectric.buildActivity(TestActivity::class.java)