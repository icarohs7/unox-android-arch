package com.github.icarohs7.app.testutils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.junit.Before
import kotlin.reflect.KClass

abstract class BaseFragmentTestClass<A : AppCompatActivity, T : Fragment>(clazz: KClass<A>, @IdRes val container: Int)
    : BaseActivityTestClass<A>(clazz) {

    @Before
    fun bootstrapActivity() {
        launchAct()
    }

    protected val fragment: T get() = activity.supportFragmentManager.findFragmentById(container) as T
}