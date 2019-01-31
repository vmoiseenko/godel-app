package com.godeltech.simpleapp.utils

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import com.godeltech.simpleapp.TestApplication
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins

class UiTestRunner : AndroidJUnitRunner() {

    override fun onStart() {
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("rxjava 2.0 initIoSchedulerHandler"))
        super.onStart()
    }

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }

}