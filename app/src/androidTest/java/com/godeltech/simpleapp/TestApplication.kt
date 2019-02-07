package com.godeltech.simpleapp

import com.godeltech.simpleapp.di.component.DaggerAppComponent
import com.godeltech.simpleapp.di.module.AppModule
import com.godeltech.simpleapp.di.module.DataModule
import com.godeltech.simpleapp.di.module.MockDataModule
import com.godeltech.simpleapp.network.MockNetworkModule

class TestApplication : BaseApplication(){

    override fun setup() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(MockNetworkModule())
            .dataModule(MockDataModule())
            .build()
    }
}