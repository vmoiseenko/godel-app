package com.godeltech.simpleapp.ui.splash

import com.godeltech.simpleapp.ui.base.BasePresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class SplashPresenter @Inject constructor(splashDelay: SplashDelay) : BasePresenter<SplashContract.View>(),
    SplashContract.Presenter {

    private val splashDelayCompletable =
        Completable
            .complete()
            .delay(splashDelay.delay, splashDelay.timeUnit)
            .cache()
            .observeOn(AndroidSchedulers.mainThread())

    private val compositeDisposable = CompositeDisposable()

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)

        splashDelayCompletable
            .subscribe { this.view?.launchApp() }
            .addTo(compositeDisposable)
    }

    override fun destroy() {
        compositeDisposable.dispose()
    }
}