package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.DataModule
import com.godeltech.simpleapp.di.module.MainActivityModule
import com.godeltech.simpleapp.di.module.NetworkModule
import com.godeltech.simpleapp.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivityModule::class, NetworkModule::class, DataModule::class])
interface MainActivityComponent {

    fun inject(activity: MainActivity)

}