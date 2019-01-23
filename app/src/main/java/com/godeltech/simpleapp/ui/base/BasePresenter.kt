package com.godeltech.simpleapp.ui.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.support.annotation.CallSuper
import android.util.Log

open class BasePresenter<V : BaseContract.View> : LifecycleObserver, BaseContract.Presenter<V> {

    var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detachView() {
        Log.d("TEST", this.javaClass.name + " detachView()")
        view = null
    }

    override fun attachLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun detachLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }
}