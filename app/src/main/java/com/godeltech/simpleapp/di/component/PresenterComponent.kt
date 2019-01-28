package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.PresenterModule
import com.godeltech.simpleapp.di.scope.PresenterScope
import com.godeltech.simpleapp.ui.test.TestContract
import dagger.Component

@PresenterScope
@Component(
    dependencies = [AppComponent::class],
    modules = [PresenterModule::class]
)
interface PresenterComponent {
    fun testPresenter(): TestContract.Presenter
}