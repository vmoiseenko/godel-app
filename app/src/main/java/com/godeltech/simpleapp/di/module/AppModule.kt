package com.godeltech.simpleapp.di.module

import android.app.Application
import com.godeltech.simpleapp.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val baseApplication: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return baseApplication
    }

}