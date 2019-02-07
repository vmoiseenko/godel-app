package com.godeltech.simpleapp.ui.history

import android.annotation.SuppressLint
import com.godeltech.simpleapp.repository.DatabaseRepository
import com.godeltech.simpleapp.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HistoryPresenter @Inject constructor(private var databaseRepository: DatabaseRepository) :
    BasePresenter<HistoryContact.View>(),
    HistoryContact.Presenter {

    @SuppressLint("CheckResult")
    override fun loadFromDb() {
        databaseRepository.readHistoryFromDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view?.showHistoryData(it) }
    }

    override fun onMenuItemClick(id: Int) {
        when (id) {
            android.R.id.home -> view?.onBackButtonClick()
        }
    }
}