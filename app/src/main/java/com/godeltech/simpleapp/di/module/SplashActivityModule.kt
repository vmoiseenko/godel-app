package com.godeltech.simpleapp.di.module

import android.app.Activity
import com.godeltech.simpleapp.ui.splash.SplashContract
import com.godeltech.simpleapp.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule(private var activity: Activity) {

    @Provides
    fun providePresenter(): SplashContract.Presenter {
        return SplashPresenter()
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

}