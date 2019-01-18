package com.godeltech.simpleapp.di.module

import android.app.Activity
import com.godeltech.simpleapp.ui.main.MainContract
import com.godeltech.simpleapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private var activity: Activity) {

    @Provides
    fun providePresenter(): MainContract.Presenter{
        return MainPresenter()
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }


}