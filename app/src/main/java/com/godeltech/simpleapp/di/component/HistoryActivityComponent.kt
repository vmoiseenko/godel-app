package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.HistoryActivityModule
import com.godeltech.simpleapp.di.scope.ActivityScope
import com.godeltech.simpleapp.ui.history.HistoryActivity
import dagger.Component

@ActivityScope
@Component(modules = [HistoryActivityModule::class], dependencies = [AppComponent::class])
interface HistoryActivityComponent {

    fun inject(activity: HistoryActivity)

}