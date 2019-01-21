package com.godeltech.simpleapp.ui.main

import android.annotation.SuppressLint
import android.util.Log
import com.godeltech.simpleapp.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import okio.BufferedSource
import java.io.IOException

class MainInteractor(var presenter: MainPresenter, private var dataRepository: DataRepository) {

    lateinit var url: String
    private val wordsMap: HashMap<String, Int> = hashMapOf()

    @SuppressLint("CheckResult")
    fun requestData() {

        dataRepository.getTextFile(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMap { responseBody: ResponseBody -> readStream(responseBody.source()) }
            .doOnComplete { presenter.onGetDataSuccess(mapToSortedList(wordsMap)) }
            .doOnError { error -> presenter.onGetDataError(error) }
            .subscribe { text: String -> onSubDataReceived(text, wordsMap) }

    }

    private fun onSubDataReceived(text: String, wordsMap: HashMap<String, Int>): HashMap<String, Int> {
        val tempMap = calculateWordsInText(text)
        return mergeMaps(tempMap, wordsMap)
    }

    private fun mapToSortedList(map: HashMap<String, Int>) =
        map.toList().sortedByDescending { it.second }


    private fun mergeMaps(firstMap: Map<String, Int>, secondMap: HashMap<String, Int>): HashMap<String, Int> {
        firstMap.keys.forEach {
            if (secondMap.containsKey(it))
                secondMap[it] = secondMap[it]!! + firstMap[it]!!
            else
                secondMap[it] = firstMap[it]!!
        }
        return secondMap
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
}