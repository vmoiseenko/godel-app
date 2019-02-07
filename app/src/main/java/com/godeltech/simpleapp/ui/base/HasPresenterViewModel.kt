package com.godeltech.simpleapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

open class HasPresenterViewModel<out PRESENTER : BaseContract.Presenter<*>>(block: () -> PRESENTER) : ViewModel() {

    val presenter: PRESENTER by lazy(block)

    override fun onCleared() {
        presenter.destroy()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory<PRESENTER : BaseContract.Presenter<*>>(private val block: () -> PRESENTER) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HasPresenterViewModel(block) as T
        }
    }
}