package com.godeltech.simpleapp.di.module

import android.app.Activity
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.ui.splash.SplashContract
import com.godeltech.simpleapp.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule(private var activity: Activity) {

    @Provides
    fun providePresenter(): SplashContract.Presenter {
        val delay = activity.resources.getInteger(R.integer.splash_delay).toLong()
        return SplashPresenter(delay)
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

}