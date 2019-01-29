package com.godeltech.simpleapp.ui.splash

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.godeltech.simpleapp.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashPresenter(private val splashDelay: Long) : BasePresenter<SplashContract.View>(), SplashContract.Presenter {

    private val disposables = CompositeDisposable()
    private var isAlive = true

    override fun detachView() {
        super.detachView()
        disposables.clear()
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun runApp() {
        Log.d("TEST", javaClass.name + "value = Lifecycle.Event.ON_START ")
        if (disposables.size() == 0) {
            Log.d("TEST", javaClass.name + "value = Lifecycle.Event.ON_START disposables.add")
            disposables.add(
                Observable.intervalRange(0, splashDelay, 0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getSubscriber())
            )
        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    private fun onLifecyclePaused() {
        Log.d("TEST", javaClass.name + " value = Lifecycle.Event.ON_PAUSE")
        isAlive = false
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        Log.d("TEST", javaClass.name + " value = Lifecycle.Event.ON_RESUME")
        isAlive = true
    }

    fun getSubscriber(): DisposableObserver<Long> {

        return object : DisposableObserver<Long>() {
            override fun onNext(t: Long) {}

            override fun onError(t: Throwable) {
                disposables.clear()
            }

            override fun onComplete() {
                Log.d("TEST", javaClass.name + " " + "DisposableObserver Done")
                if (isAlive) view?.launchApp()
                disposables.clear()
            }
        }
    }


}