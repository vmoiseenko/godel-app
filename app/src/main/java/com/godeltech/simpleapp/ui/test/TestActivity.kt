package com.godeltech.simpleapp.ui.test

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import com.godeltech.simpleapp.di.component.DaggerTestComponent
import com.godeltech.simpleapp.di.component.PresenterComponent
import com.godeltech.simpleapp.ui.base.BaseAppCompatActivity
import javax.inject.Inject

@SuppressLint("Registered")
class TestActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var presenter: TestContract.Presenter

    override fun <T : AppCompatActivity> injectDependencies(presenterComponent: PresenterComponent, instance: T) {
        DaggerTestComponent.builder()
            .presenterComponent(presenterComponent)
            .build()
            .inject(this)
    }
}