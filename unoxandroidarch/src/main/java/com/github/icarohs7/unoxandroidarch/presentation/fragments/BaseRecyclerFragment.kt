package com.github.icarohs7.unoxandroidarch.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.databinding.FragmentBaseRecyclerBinding
import com.umutbey.stateviews.StateView

/**
 * Extension of [BaseBindingFragment] using a layout
 * containing a fullscreen [StateView] with a [RecyclerView]
 * inside
 */
abstract class BaseRecyclerFragment : BaseBindingFragment<FragmentBaseRecyclerBinding>() {
    override fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        super.onBindingCreated(inflater, container, savedInstanceState)
        binding.stateView.hideStates()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        onRecyclerSetup(binding.recycler)
    }

    abstract fun onRecyclerSetup(recycler: RecyclerView)

    override fun getLayout(): Int {
        return R.layout.fragment_base_recycler
    }
}