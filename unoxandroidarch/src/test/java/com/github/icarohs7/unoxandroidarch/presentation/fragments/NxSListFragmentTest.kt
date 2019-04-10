package com.github.icarohs7.unoxandroidarch.presentation.fragments

import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
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
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class NxSListFragmentTest {
    @Test
    fun `should start fragment`() {
        val (controller, _, fragment) = mockFragment<Frag>()
        controller.start()
        fragment.binding.recycler.adapter?.itemCount shouldEqual 1
    }

    data class State(val value: Int = 0) : MvRxState
    class Frag : NxSListFragment<State, FragmentBaseRecyclerBinding, Int, MockViewBinding>() {
        override val viewmodel: SimpleRxMvRxViewModel<State> by fragmentViewModel()
        override val stateStream: Flowable<State> = Flowable.just(State(42))
        override val recycler: () -> RecyclerView = { binding.recycler }
        override val itemLayout: Int = R.layout.mock_view

        override fun invalidate(): Unit = withState(viewmodel) { state ->
            super.invalidate()
            loadList(listOf(state.value))
        }

        override fun renderItem(item: Int, view: MockViewBinding, position: Int) {
            view.txtMessage.text = "$item"
        }

        override fun transformDataSource(state: State): List<Int> {
            return listOf(state.value)
        }

        override fun getLayout(): Int {
            return R.layout.fragment_base_recycler
        }
    }
}