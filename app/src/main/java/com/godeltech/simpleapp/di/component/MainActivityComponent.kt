package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.MainActivityModule
import com.godeltech.simpleapp.ui.main.MainActivity
import dagger.Component

@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun inject(activity: MainActivity)

}