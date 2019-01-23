package com.godeltech.simpleapp.ui.splash

import com.godeltech.simpleapp.ui.base.BaseContract

class SplashContract {

    interface View : BaseContract.View {
        fun launchApp()
    }

    interface Presenter : BaseContract.Presenter<SplashContract.View>
}