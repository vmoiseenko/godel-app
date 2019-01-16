package com.godeltech.simpleapp.ui.main

import android.annotation.SuppressLint
import android.util.Patterns
import com.godeltech.simpleapp.network.NetworkService
import com.godeltech.simpleapp.utils.FileUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File

class MainPresenter : MainContract.Presenter {

    lateinit var view: MainContract.View

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {
    }

    override fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    @SuppressLint("CheckResult")
    override fun requestData(url: String) {
        //http://25.io/toau/audio/sample.txt

        onProgressShow()

        NetworkService.create().downloadFileWithUrl(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response: ResponseBody? ->
                    FileUtils.writeResponseBodyToDisk(view.getFilePath(), response)
                    publishResult(url, calculateWordsInFile(view.getFilePath()))
                    onProgressHide()
                },
                { error ->
                    onProgressHide()
                    view.onError(error)
                })

    }

    override fun calculateWordsInFile(filePath: String): Int {
        return File(filePath).readText().split(" ").size
    }

    private fun publishResult(url: String, count: Int) {
        view.updateListData(Pair(url, count.toString()))
    }

    private fun onProgressShow() {
        view.showProgress()
        view.setTextFieldState(false)
        view.setActionButtonState(false)
    }

    private fun onProgressHide() {
        view.hideProgress()
        view.setTextFieldState(true)
        view.setActionButtonState(true)
    }

}