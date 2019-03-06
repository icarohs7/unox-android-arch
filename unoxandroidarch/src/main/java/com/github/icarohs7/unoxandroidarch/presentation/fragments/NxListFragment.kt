package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.databinding.ViewDataBinding

/**
 * Simplified version of [NxSListFragment],
 * using a List<[I]> as the type of State,
 * instead of needing a fourth type parameter
 */
abstract class NxListFragment<DB : ViewDataBinding, I, IDB : ViewDataBinding> : NxSListFragment<List<I>, DB, I, IDB>()