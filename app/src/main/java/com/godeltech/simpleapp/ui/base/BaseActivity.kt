package com.godeltech.simpleapp.ui.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity


abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> : AppCompatActivity(),
    BaseContract.View,
    LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)
    lateinit var presenter: P

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(BaseViewModel<V, P>()::class.java)

        if (viewModel.getPresenter() == null) {
            injectDependency()
            initPresenter()
            viewModel.setPresenter(presenter)
        }

        presenter = viewModel.getPresenter()!!
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    override fun onDestroy() {
        presenter.detachLifecycle(lifecycle)
        super.onDestroy()
    }

    abstract fun initPresenter()

    abstract fun injectDependency()
}