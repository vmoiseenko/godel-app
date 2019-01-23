package com.godeltech.simpleapp.ui.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.util.Patterns
import com.godeltech.simpleapp.repository.DataRepository
import com.godeltech.simpleapp.ui.base.BasePresenter

class MainPresenter(private val dataRepository: DataRepository) : BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    private lateinit var interactor: MainInteractor

    override fun attachView(view: MainContract.View) {
        this.view = view
        if (!::interactor.isInitialized) {
            interactor = MainInteractor(this, dataRepository)
        }
    }

    override fun detachView() {
        super.detachView()
        interactor.unsubscribe()
    }

    override fun onUrlTextChanged(url: String) {
        if (interactor.isOperationActive()) return
        view?.setActionButtonEnabled(isUrlValid(url))
        interactor.url = url
    }

    override fun onActionButtonClick() {
        onProgressShow()
        interactor.requestData()
    }

    fun onGetDataSuccess(wordGroups: List<Pair<String, Int>>) {
        onProgressHide()
        view?.addListData(wordGroups)
    }

    fun onGetDataError(error: Throwable) {
        onProgressHide()
        view?.showError(error)
    }

    private fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    private fun onProgressShow() {
        view?.showProgress()
        view?.setUrlTextFieldEnabled(false)
        view?.setActionButtonEnabled(false)
    }

    private fun onProgressHide() {
        view?.hideProgress()
        view?.setUrlTextFieldEnabled(true)
        view?.setActionButtonEnabled(true)
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        Log.d("TEST", "Lifecycle.Event.ON_CREATE ::interactor.isInitialized " + ::interactor.isInitialized)
        if (::interactor.isInitialized) {
            view?.addListData(interactor.getCachedData())
        }
        Log.d("TEST", "Lifecycle.Event.ON_CREATE interactor.isOperationActive() " + interactor.isOperationActive())
        if (interactor.isOperationActive()) onProgressShow()

    }


}