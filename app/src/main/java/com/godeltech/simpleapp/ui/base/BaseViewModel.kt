package com.godeltech.simpleapp.ui.base

import android.arch.lifecycle.ViewModel
import android.util.Log

open class BaseViewModel<V : BaseContract.View, P : BaseContract.Presenter<V>> : ViewModel() {

    private var presenter: P? = null

    fun getPresenter(): P? {
        return presenter
    }

    fun setPresenter(p: P) {
        if (presenter == null) {
            presenter = p
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TEST", javaClass.name + " onCleared()")
        presenter?.detachView()
        presenter = null
    }
}