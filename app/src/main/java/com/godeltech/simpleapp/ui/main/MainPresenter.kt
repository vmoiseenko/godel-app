package com.godeltech.simpleapp.ui.main

import android.annotation.SuppressLint
import android.util.Patterns
import com.godeltech.simpleapp.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import okio.BufferedSource
import java.io.IOException

class MainPresenter(private val dataRepository: DataRepository) : MainContract.Presenter {

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
        //https://norvig.com/big.txt
        onProgressShow()
        val wordsMap: HashMap<String, Int> = HashMap()
        dataRepository.getTextFile(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMap { responseBody: ResponseBody -> readStream(responseBody.source()) }
            .doOnComplete {
                onProgressHide()
                publishResult(wordsMap.toList().sortedByDescending { it.second })
            }
            .doOnError { error ->
                run {
                    view.showError(error)
                    onProgressHide()
                }
            }
            .subscribe { text: String ->
                run {
                    val tempMap = calculateWordsInText(text)
                    mergeMaps(tempMap, wordsMap)
                }
            }

    }

    private fun mergeMaps(firstMap: Map<String, Int>, secondMap: HashMap<String, Int>): HashMap<String, Int> {
        firstMap.keys.forEach {
            if (secondMap.containsKey(it))
                secondMap[it] = secondMap[it]!! + firstMap[it]!!
            else
                secondMap[it] = firstMap[it]!!
        }
        return secondMap
    }

    private fun readStream(source: BufferedSource): Observable<String> {
        return Observable
            .create { emitter ->
                try {
                    while (!source.exhausted()) {
                        emitter.onNext(source.readUtf8Line()!!)
                    }
                    emitter.onComplete()

                } catch (e: IOException) {
                    e.printStackTrace()
                    emitter.onError(e)
                }
            }
    }

    @Suppress("UnnecessaryVariable")
    private fun calculateWordsInText(text: String): Map<String, Int> {
        val r = Regex("""\p{javaLowerCase}+""")
        val matches = r.findAll(text.toLowerCase())
        val wordGroups = matches.map { it.value }
            .groupBy { it }
            .mapValues { it.key; it.value.size }

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

}