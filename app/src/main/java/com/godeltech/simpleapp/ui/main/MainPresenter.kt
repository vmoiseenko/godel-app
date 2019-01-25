package com.godeltech.simpleapp.ui.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.godeltech.simpleapp.ui.base.BasePresenter
import com.godeltech.simpleapp.utils.Validator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class MainPresenter(private var interactor: MainInteractor, var validator: Validator) : BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    private val disposables = CompositeDisposable()

    override fun detachView() {
        super.detachView()
        disposables.dispose()
    }

    override fun onUrlTextChanged(url: String) {
        if (isHasSubscriptions()) return
        view?.setActionButtonEnabled(validator.isUrlValid(url))
        interactor.setUrl(url)
    }

    override fun onActionButtonClick() {
        onProgressShow()
        disposables.add(
            interactor.requestData()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { onGetDataSuccess(it) }
                .doOnError { onGetDataError(it) }
                .doOnComplete { disposables.clear() }
                .subscribe())
    }

    private fun onGetDataSuccess(data: List<Pair<String, Int>>) {
        onProgressHide()
        view?.addListData(data)
    }

    private fun onGetDataError(t: Throwable) {
        onProgressHide()
        view?.showError(t)
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
        Log.d("TEST", "Lifecycle.Event.ON_CREATE isHasSubscriptions " + isHasSubscriptions())
        if (isHasSubscriptions()) {
            onProgressShow()
        } else {
            view?.addListData(interactor.getCachedData())
        }
    }

    private fun isHasSubscriptions() = disposables.size() > 0

}