package com.godeltech.simpleapp.di.module

import android.app.Application
import android.content.Context
import com.godeltech.simpleapp.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule(private val baseApplication: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return baseApplication
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return baseApplication
    }
}