package com.godeltech.simpleapp.ui.base

import android.arch.lifecycle.Lifecycle

class BaseContract {

    interface View

    interface Presenter<T : View> {
        fun attachView(view: T)
        fun detachView()
        fun attachLifecycle(lifecycle: Lifecycle)
        fun detachLifecycle(lifecycle: Lifecycle)
    }

}