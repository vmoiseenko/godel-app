package com.godeltech.simpleapp.ui.main

import android.annotation.SuppressLint
import android.util.Patterns
import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.repository.DataRepositoryProvider
import com.godeltech.simpleapp.utils.FileUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File

class MainPresenter : MainContract.Presenter {

    private lateinit var view: MainContract.View
    private var url: String = String()

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {
    }

    override fun onUrlTextChanged(url: String) {
        view.setActionButtonEnabled(isUrlValid(url))
        this.url = url
    }

    override fun onActionButtonClick() {
        requestData(url)
    }

    private fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    @SuppressLint("CheckResult")
    fun requestData(url: String) {
        //http://25.io/toau/audio/sample.txt

        onProgressShow()
        val dataRepository = DataRepositoryProvider.provideDataRepository()

        dataRepository.getTextFile(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response: ResponseBody? ->
                    FileUtils.writeResponseBodyToDisk(getFilePath(), response)
                    val text = openFileAsText(getFilePath())
                    val wordGroups = calculateWordsInFile(text)
                    publishResult(wordGroups)
                    onProgressHide()
                },
                { error ->
                    onProgressHide()
                    view.showError(error)
                })

    }

    private fun openFileAsText(filePath: String): String {
        return File(filePath).readText().toLowerCase()
    }

    @Suppress("UnnecessaryVariable")
    private fun calculateWordsInFile(text: String): List<Pair<String, Int>> {
        val r = Regex("""\p{javaLowerCase}+""")
        val matches = r.findAll(text)
        val wordGroups = matches.map { it.value }
            .groupBy { it }
            .map { Pair(it.key, it.value.size) }
            .sortedByDescending { it.second }

        return wordGroups
    }

    private fun publishResult(wordGroups: List<Pair<String, Int>>) {
        view.addListData(wordGroups)
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

    private fun getFilePath(): String {
        return BaseApplication.appContext?.getExternalFilesDir(null).toString() + File.separator + "file.txt"
    }

}