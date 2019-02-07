package com.godeltech.simpleapp

import android.app.Application
import com.godeltech.simpleapp.di.component.AppComponent
import com.godeltech.simpleapp.di.component.DaggerAppComponent
import com.godeltech.simpleapp.di.module.AppModule
import com.godeltech.simpleapp.di.module.DataModule
import com.godeltech.simpleapp.di.module.NetworkModule

open class BaseApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    open fun setup() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .dataModule(DataModule())
            .build()
    }

    fun getAppComponent(): AppComponent {
        return component
    }

}