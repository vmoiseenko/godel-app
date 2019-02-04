package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.ui.base.BasePresenter
import com.godeltech.simpleapp.utils.Validator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter @Inject constructor(private var interactor: MainInteractor, var validator: Validator) :
    BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    private val disposables = CompositeDisposable()
    private var isDataLoading = false
    private var isDataReady = false

    override fun attachView(view: MainContract.View) {
        super.attachView(view)

        if (isDataLoading) {
            onProgressShow()
        } else if (isDataReady) {
            view.addListData(interactor.getCachedData())
        }
    }

    override fun onUrlTextChanged(url: String) {
        if (isDataLoading) return
        if (validator.isUrlValid(url)) {
            view?.setActionButtonEnabled(true)
            interactor.setUrl(url)
        } else {
            view?.setActionButtonEnabled(false)
        }
    }

    override fun onActionButtonClick() {
        disposables.add(
            interactor.requestData()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isDataLoading = true
                    onProgressShow()
                }
                .doOnTerminate {
                    isDataLoading = false
                    onProgressHide()
                }
                .subscribe(
                    {
                        isDataReady = true
                        view?.addListData(it)
                    },
                    {
                        isDataReady = false
                        view?.showError(it)
                    }
                )
        )
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

    override fun destroy() {
        disposables.dispose()
    }

}