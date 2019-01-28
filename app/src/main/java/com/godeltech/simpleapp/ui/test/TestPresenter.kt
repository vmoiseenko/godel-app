package com.godeltech.simpleapp.ui.test

import com.godeltech.simpleapp.di.scope.PresenterScope
import com.godeltech.simpleapp.ui.base.BasePresenter
import javax.inject.Inject

@PresenterScope
class TestPresenter @Inject constructor() :
    BasePresenter<TestContract.View>(),
    TestContract.Presenter