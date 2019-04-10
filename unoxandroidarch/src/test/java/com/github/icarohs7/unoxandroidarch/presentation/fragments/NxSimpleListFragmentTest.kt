package com.github.icarohs7.unoxandroidarch.presentation.fragments

import com.airbnb.mvrx.fragmentViewModel
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.data.entities.ListState
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
class NxSimpleListFragmentTest {
    @Test
    fun `should start fragment`() {
        val (controller, _, fragment) = mockFragment<Frag>()
        controller.start()
        fragment.binding.recycler.adapter?.itemCount shouldEqual 2
    }

    class Frag : NxSimpleListFragment<Int, MockViewBinding>() {
        override val viewmodel: SimpleRxMvRxViewModel<ListState<Int>> by fragmentViewModel()
        override val itemLayout: Int = R.layout.mock_view
        override val stateStream: Flowable<ListState<Int>> = Flowable.just(ListState(listOf(42, 1532)))

        override fun renderItem(item: Int, view: MockViewBinding, position: Int) {
            view.txtMessage.text = "$item"
        }
    }
}