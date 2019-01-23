package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.SplashActivityModule
import com.godeltech.simpleapp.ui.splash.SplashActivity
import dagger.Component

@Component(modules = [SplashActivityModule::class])
interface SplashActivityComponent {

    fun inject(activity: SplashActivity)

}