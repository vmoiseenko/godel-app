package com.godeltech.simpleapp.di.module

import androidx.lifecycle.ViewModelProviders
import com.godeltech.simpleapp.ui.base.HasPresenterViewModel
import com.godeltech.simpleapp.ui.history.HistoryActivity
import com.godeltech.simpleapp.ui.history.HistoryContact
import com.godeltech.simpleapp.ui.history.HistoryPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class HistoryActivityModule(private val historyActivity: HistoryActivity) {

    @Provides
    fun providePresenter(historyPresenterProvider: Provider<HistoryPresenter>): HistoryContact.Presenter {
        return ViewModelProviders
            .of(historyActivity, HasPresenterViewModel.Factory(historyPresenterProvider::get))
            .get(HasPresenterViewModel::class.java)
            .presenter as HistoryContact.Presenter
    }
}