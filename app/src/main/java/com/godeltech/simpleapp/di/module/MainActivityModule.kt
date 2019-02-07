package com.godeltech.simpleapp.di.module

import androidx.lifecycle.ViewModelProviders
import com.godeltech.simpleapp.ui.base.HasPresenterViewModel
import com.godeltech.simpleapp.ui.main.MainActivity
import com.godeltech.simpleapp.ui.main.MainContract
import com.godeltech.simpleapp.ui.main.MainPresenter
import com.godeltech.simpleapp.utils.Validator
import com.godeltech.simpleapp.utils.ValidatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class MainActivityModule(private var mainActivity: MainActivity) {

    @Provides
    fun provideValidator(): Validator {
        return ValidatorImpl()
    }

    @Provides
    fun providePresenter(mainPresenterProvider: Provider<MainPresenter>): MainContract.Presenter {
        return ViewModelProviders
            .of(mainActivity, HasPresenterViewModel.Factory(mainPresenterProvider::get))
            .get(HasPresenterViewModel::class.java)
            .presenter as MainContract.Presenter
    }
}