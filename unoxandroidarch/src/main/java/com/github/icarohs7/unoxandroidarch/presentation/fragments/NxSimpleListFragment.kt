package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.databinding.FragmentBaseRecyclerBinding

abstract class NxSimpleListFragment<I, IDB : ViewDataBinding> : NxListFragment<FragmentBaseRecyclerBinding, I, IDB>() {
    override fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        super.onBindingCreated(inflater, container, savedInstanceState)
        binding.stateView.hideStates()
        config.recyclerFn = { binding.recycler }
    }

    /**
     * Display a state with the given tag
     * on the state view of the layout
     */
    fun displayState(stateTag: String) {
        binding.stateView.displayState(stateTag)
    }

    override fun render(state: List<I>) {
        loadItems(state)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_base_recycler
    }
}