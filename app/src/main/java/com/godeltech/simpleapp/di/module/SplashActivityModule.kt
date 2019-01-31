package com.godeltech.simpleapp.di.module

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.ui.base.HasPresenterViewModel
import com.godeltech.simpleapp.ui.splash.SplashActivity
import com.godeltech.simpleapp.ui.splash.SplashContract
import com.godeltech.simpleapp.ui.splash.SplashDelay
import com.godeltech.simpleapp.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Provider


@Module
class SplashActivityModule(private val splashActivity: SplashActivity) {

    @Provides
    fun providePresenter(splashPresenterProvider: Provider<SplashPresenter>): SplashContract.Presenter {
        return ViewModelProviders
            .of(splashActivity, HasPresenterViewModel.Factory(splashPresenterProvider::get))
            .get(HasPresenterViewModel::class.java)
            .presenter as SplashContract.Presenter
    }

    @Provides
    fun providesSplashDelay(context: Context): SplashDelay {
        return SplashDelay(context.resources.getInteger(R.integer.splash_delay).toLong(), TimeUnit.SECONDS)
    }
}