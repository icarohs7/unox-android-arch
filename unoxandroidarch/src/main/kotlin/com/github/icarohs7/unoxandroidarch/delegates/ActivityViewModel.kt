package com.github.icarohs7.unoxandroidarch.delegates

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T : ViewModel> AppCompatActivity.activityViewModel(): ActivityViewModelDelegate<T> {
    return ActivityViewModelDelegate(this, T::class)
}

class ActivityViewModelDelegate<T : ViewModel>(
        activity: AppCompatActivity,
        vmClass: KClass<T>
) : ReadOnlyProperty<Any?, T> {
    private val vm by lazy { ViewModelProviders.of(activity).get(vmClass.java) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return vm
    }
}