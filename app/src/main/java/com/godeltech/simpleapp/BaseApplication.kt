package com.godeltech.simpleapp

import android.app.Application
import com.godeltech.simpleapp.di.component.AppComponent
import com.godeltech.simpleapp.di.component.DaggerAppComponent
import com.godeltech.simpleapp.di.module.AppModule

class BaseApplication : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    fun setup() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

    fun getAppComponent(): AppComponent {
        return component
    }

}