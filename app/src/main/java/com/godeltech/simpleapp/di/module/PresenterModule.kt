package com.godeltech.simpleapp.di.module

import com.godeltech.simpleapp.ui.test.TestContract
import com.godeltech.simpleapp.ui.test.TestPresenter
import dagger.Binds
import dagger.Module

@Module
interface PresenterModule {

    @Binds
    fun bindsToTestContractPresenter(testPresenter: TestPresenter): TestContract.Presenter
}