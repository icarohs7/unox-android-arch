package com.github.icarohs7.unoxandroidarch.extensions

import com.umutbey.stateviews.StateView

/**
 * Display the loading state if the parameter
 * is true of hide all states if false
 */
fun StateView.toggleLoading(isLoading: Boolean) {
    if (isLoading) displayLoadingState()
    else hideStates()
}