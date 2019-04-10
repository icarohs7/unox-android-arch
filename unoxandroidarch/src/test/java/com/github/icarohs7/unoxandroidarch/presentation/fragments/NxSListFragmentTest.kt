package com.github.icarohs7.unoxandroidarch.presentation.fragments

import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.fragmentViewModel
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.databinding.FragmentBaseRecyclerBinding
import com.github.icarohs7.unoxandroidarch.databinding.MockViewBinding
import com.github.icarohs7.unoxandroidarch.mockFragment
import com.github.icarohs7.unoxandroidarch.presentation.viewmodel.SimpleRxMvRxViewModel
import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class NxSListFragmentTest {
    @Test
    fun `should start fragment`() {
        val (controller, _, fragment) = mockFragment<Frag>()
        controller.start()
        fragment.binding
    }

    data class State(val value: Int = 0) : MvRxState
    class Frag : NxSListFragment<State, FragmentBaseRecyclerBinding, Int, MockViewBinding>() {
        override val viewmodel: SimpleRxMvRxViewModel<State> by fragmentViewModel()

        override fun onSetup(config: Configuration<State>): Unit = with(config) {
            layout = R.layout.fragment_base_recycler
            itemLayout = R.layout.mock_view
            stateStream = Flowable.just(State(42))
            recycler = { binding.recycler }
        }

        override fun renderItem(item: Int, view: MockViewBinding, position: Int) {
            view.txtMessage.text = "$item"
        }
    }
}