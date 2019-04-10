package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding
import com.github.icarohs7.unoxandroidarch.data.entities.ListState

/**
 * Simplified version of [NxSListFragment],
 * using a List<[I]> as the type of State
 * which is wrapped on an instance of [ListState],
 * instead of needing a fourth type parameter
 */
abstract class NxListFragment<DB : ViewDataBinding, I, IDB : ViewDataBinding>
    : NxSListFragment<ListState<I>, DB, I, IDB>() {
    override fun transformDataSource(state: ListState<I>): List<I> {
        return state.value
    }
}