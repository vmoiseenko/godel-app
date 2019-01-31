package com.godeltech.simpleapp.ui.splash

import android.content.Intent
import android.os.Bundle
import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.di.component.DaggerSplashActivityComponent
import com.godeltech.simpleapp.di.module.SplashActivityModule
import com.godeltech.simpleapp.ui.base.BaseActivity
import com.godeltech.simpleapp.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    lateinit var splashPresenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashPresenter.attachView(this)
    }

    override fun onDestroy() {
        splashPresenter.detachView()
        super.onDestroy()
    }

    override fun injectDependency() {
        val baseApplication = applicationContext as BaseApplication

        val activityComponent = DaggerSplashActivityComponent.builder()
            .splashActivityModule(SplashActivityModule(this))
            .appComponent(baseApplication.getAppComponent())
            .build()

        activityComponent.inject(this)
    }

    override fun launchApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
