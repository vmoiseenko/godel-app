package com.godeltech.simpleapp.ui.base

import androidx.lifecycle.Lifecycle

class BaseContract {

    interface View

    interface Presenter<T : View> {
        fun attachView(view: T)
        fun detachView()
        fun attachLifecycle(lifecycle: Lifecycle)
        fun detachLifecycle(lifecycle: Lifecycle)
        fun destroy()
    }

}