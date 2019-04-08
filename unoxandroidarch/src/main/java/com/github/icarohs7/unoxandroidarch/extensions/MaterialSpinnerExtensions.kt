package com.github.icarohs7.unoxandroidarch.extensions

import com.jaredrummler.materialspinner.MaterialSpinner

/**
 * Return the currently selected item
 * on the spinner
 */
fun <T> MaterialSpinner.selectedItem(): T {
    val index = this.selectedIndex
    return this.getItems<T>()[index]
}

/**
 * Select the given item on the spinner
 */
fun <T> MaterialSpinner.selectItem(item: T) {
    getItems<T>().forEachIndexed { index, i ->
        if (i == item) {
            selectedIndex = index
            return@forEachIndexed
        }
    }
}