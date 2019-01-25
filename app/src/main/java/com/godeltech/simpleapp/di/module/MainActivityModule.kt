package com.godeltech.simpleapp.di.module

import android.app.Activity
import android.util.Patterns
import com.godeltech.simpleapp.repository.DataRepository
import com.godeltech.simpleapp.ui.main.MainContract
import com.godeltech.simpleapp.ui.main.MainInteractor
import com.godeltech.simpleapp.ui.main.MainPresenter
import com.godeltech.simpleapp.utils.Validator
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private var activity: Activity) {

    @Provides
    fun provideValidator(): Validator{
        return object : Validator{
            override fun isUrlValid(url: String): Boolean {
                return Patterns.WEB_URL.matcher(url).matches()
            }
        }
    }

    @Provides
    fun provideInteractor(dataRepository: DataRepository): MainInteractor{
        return MainInteractor(dataRepository)
    }

    @Provides
    fun providePresenter(interactor: MainInteractor, validator: Validator): MainContract.Presenter {
        return MainPresenter(interactor, validator)
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

}