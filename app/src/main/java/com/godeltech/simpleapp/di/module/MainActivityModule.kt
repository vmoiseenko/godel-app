package com.godeltech.simpleapp.di.module

import android.arch.lifecycle.ViewModelProviders
import android.util.Patterns
import com.godeltech.simpleapp.ui.base.HasPresenterViewModel
import com.godeltech.simpleapp.ui.main.MainActivity
import com.godeltech.simpleapp.ui.main.MainContract
import com.godeltech.simpleapp.ui.main.MainPresenter
import com.godeltech.simpleapp.utils.Validator
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class MainActivityModule(private var mainActivity: MainActivity) {

    @Provides
    fun provideValidator(): Validator {
        return object : Validator {
            override fun isUrlValid(url: String): Boolean {
                return Patterns.WEB_URL.matcher(url).matches()
            }
        }
    }

    @Provides
    fun providePresenter(mainPresenterProvider: Provider<MainPresenter>): MainContract.Presenter {
        return ViewModelProviders
            .of(mainActivity, HasPresenterViewModel.Factory(mainPresenterProvider::get))
            .get(HasPresenterViewModel::class.java)
            .presenter as MainContract.Presenter
    }
}