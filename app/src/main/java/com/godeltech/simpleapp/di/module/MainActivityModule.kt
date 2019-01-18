package com.godeltech.simpleapp.di.module

import android.app.Activity
import com.godeltech.simpleapp.repository.DataRepository
import com.godeltech.simpleapp.ui.main.MainContract
import com.godeltech.simpleapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private var activity: Activity) {

    @Provides
    fun providePresenter(dataRepository: DataRepository): MainContract.Presenter {
        return MainPresenter(dataRepository)
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

}