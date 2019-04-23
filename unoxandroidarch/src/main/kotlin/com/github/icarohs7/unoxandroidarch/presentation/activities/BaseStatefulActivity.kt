package com.github.icarohs7.unoxandroidarch.presentation.activities

import androidx.databinding.ViewDataBinding
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onStop

abstract class BaseStatefulActivity<S, DB : ViewDataBinding> : BaseBindingActivity<DB>() {

    override fun onStart() {
        super.onStart()
        onGetStateStream()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onNewState)
                .disposeBy(onStop)
    }

    abstract fun onGetStateStream(): Flowable<S>
    abstract fun onNewState(state: S)
}