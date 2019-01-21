package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.MainActivityModule
import com.godeltech.simpleapp.di.scope.ActivityScope
import com.godeltech.simpleapp.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = [MainActivityModule::class], dependencies = [AppComponent::class])
interface MainActivityComponent {

    fun inject(activity: MainActivity)

}