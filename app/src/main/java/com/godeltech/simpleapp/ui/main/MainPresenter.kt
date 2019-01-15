package com.godeltech.simpleapp.ui.main

import android.os.Handler


class MainPresenter : MainContract.Presenter {

    lateinit var view: MainContract.View

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {
    }

    override fun onActionButtonClick() {
        view.showProgress()
        val handler = Handler()

        handler.postDelayed({view.hideProgress()}, 2000)

//        runBlocking {
//            delay(2000)
//            view.hideProgress()
//        }
    }



}