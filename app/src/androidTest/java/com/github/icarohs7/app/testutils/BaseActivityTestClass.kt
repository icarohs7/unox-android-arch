package com.github.icarohs7.app.testutils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.Rule
import kotlin.reflect.KClass


abstract class BaseActivityTestClass<T : AppCompatActivity>(clazz: KClass<T>) {
    @get:Rule
    val rule: ActivityTestRule<T> = ActivityTestRule(clazz.java, false, false)
    @get:Rule
    val eraseSharedPrefsRule: ClearPreferencesRule = ClearPreferencesRule()
    val activity: T get() = rule.activity

    protected open fun launchAct(vararg args: Pair<String, Any> = emptyArray()) {
        rule.launchActivity(Intent().apply { putExtras(bundleOf(*args)) })
    }
}