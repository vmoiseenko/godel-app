package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.di.module.AppModule
import com.godeltech.simpleapp.di.module.DataModule
import com.godeltech.simpleapp.di.module.NetworkModule
import com.godeltech.simpleapp.network.NetworkService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DataModule::class])
interface AppComponent {

    fun inject(application: BaseApplication)

    fun getNetworkService(): NetworkService

}