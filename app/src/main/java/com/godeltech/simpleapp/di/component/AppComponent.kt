package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.di.module.AppModule
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(application: BaseApplication)

}