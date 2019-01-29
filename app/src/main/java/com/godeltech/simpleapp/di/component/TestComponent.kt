package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.TestActivityModule
import com.godeltech.simpleapp.di.scope.ActivityScope
import com.godeltech.simpleapp.ui.test.TestActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [PresenterComponent::class],
    modules = [TestActivityModule::class]
)
interface TestComponent {
    fun inject(testActivity: TestActivity)
}