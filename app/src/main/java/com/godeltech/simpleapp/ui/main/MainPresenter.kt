package com.godeltech.simpleapp.ui.main

import android.util.Patterns
import com.godeltech.simpleapp.repository.DataRepository

class MainPresenter(private val dataRepository: DataRepository) : MainContract.Presenter {

    private lateinit var view: MainContract.View

    private lateinit var interactor: MainInteractor

    override fun attach(view: MainContract.View) {
        this.view = view
        interactor = MainInteractor(this, dataRepository)
    }

    override fun detach() {
    }

    override fun onUrlTextChanged(url: String) {
        view.setActionButtonEnabled(isUrlValid(url))
        interactor.url = url
    }

    override fun onActionButtonClick() {
        onProgressShow()
        interactor.requestData()
    }

    fun onGetDataSuccess(wordGroups: List<Pair<String, Int>>){
        onProgressHide()
        view.addListData(wordGroups)
    }

    fun onGetDataError(error: Throwable){
        onProgressHide()
        view.showError(error)
    }

    private fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    private fun onProgressShow() {
        view.showProgress()
        view.setUrlTextFieldEnabled(false)
        view.setActionButtonEnabled(false)
    }

    private fun onProgressHide() {
        view.hideProgress()
        view.setUrlTextFieldEnabled(true)
        view.setActionButtonEnabled(true)
    }

}