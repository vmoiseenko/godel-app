package com.godeltech.simpleapp.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.di.component.AppComponent
import com.godeltech.simpleapp.di.component.DaggerPresenterComponent
import com.godeltech.simpleapp.di.component.PresenterComponent

abstract class BaseAppCompatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(getPresenterComponent(), this)
    }

    protected abstract fun <T : AppCompatActivity> injectDependencies(
        presenterComponent: PresenterComponent,
        instance: T
    )

    private fun getPresenterComponent(): PresenterComponent {
        return getPresenterComponentViewModel().presenterComponent
    }

    private fun getPresenterComponentViewModel(): PresenterComponentViewModel {
        return ViewModelProviders
            .of(this, PresenterComponentViewModelFactory(this))
            .get(PresenterComponentViewModel::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    protected class PresenterComponentViewModelFactory(private val appCompatActivity: AppCompatActivity) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PresenterComponentViewModel(appCompatActivity) as T
        }
    }

    private class PresenterComponentViewModel(val presenterComponent: PresenterComponent) : ViewModel() {
        constructor(appComponent: AppComponent) : this(DaggerPresenterComponent.builder().appComponent(appComponent).build())
        constructor(baseApplication: BaseApplication) : this(baseApplication.getAppComponent())
        constructor(appCompatActivity: AppCompatActivity) : this(appCompatActivity.application as BaseApplication)
    }
}