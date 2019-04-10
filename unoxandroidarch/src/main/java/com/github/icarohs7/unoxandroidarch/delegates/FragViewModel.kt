package com.github.icarohs7.unoxandroidarch.delegates

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T : ViewModel> Fragment.fragViewModel(): FragViewModelDelegate<T> {
    return FragViewModelDelegate(this, T::class)
}

class FragViewModelDelegate<T : ViewModel>(
        frag: Fragment,
        vmClass: KClass<T>
) : ReadOnlyProperty<Any?, T> {
    private val vm by lazy { ViewModelProviders.of(frag).get(vmClass.java) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return vm
    }
}