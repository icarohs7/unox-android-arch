package com.github.icarohs7.unoxandroidarch.presentation.fragments

import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.fragmentViewModel
import com.github.icarohs7.unoxandroidarch.R
import com.github.icarohs7.unoxandroidarch.TestApplication
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
class NxFragmentTest {
    @Test
    fun `should start fragment`() {
        val (controller, _, fragment) = mockFragment<Frag>()
        controller.start()
        fragment.binding.txtMessage.text.toString() shouldEqual "Hello, World!"
    }

    data class State(val value: Int = 0) : MvRxState
    class Frag : NxFragment<State, MockViewBinding>() {
        override val viewmodel: SimpleRxMvRxViewModel<State> by fragmentViewModel()

        override fun onSetup(config: Configuration<State>) = with(config) {
            layout = R.layout.mock_view
            stateStream = Flowable.just(State(1))
        }
    }
}