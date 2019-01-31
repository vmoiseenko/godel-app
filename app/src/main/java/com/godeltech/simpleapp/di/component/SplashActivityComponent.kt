package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.SplashActivityModule
import com.godeltech.simpleapp.di.scope.ActivityScope
import com.godeltech.simpleapp.ui.splash.SplashActivity
import dagger.Component

@ActivityScope
@Component(modules = [SplashActivityModule::class], dependencies = [AppComponent::class])
interface SplashActivityComponent {

    fun inject(activity: SplashActivity)

}