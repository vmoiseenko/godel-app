package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import okio.BufferedSource

open class MainInteractor(var dataRepository: DataRepository) {

    private lateinit var url: String

    private val wordsMap: HashMap<String, Int> = hashMapOf()

    fun requestData(): Observable<List<Pair<String, Int>>> {
        return dataRepository.getTextFile(url)
            .flatMap { responseBody: ResponseBody -> readStream(responseBody.source()) } //Show that call was success
            .doOnNext { onSubDataReceived(it, wordsMap) }
            .doOnError {
                ObservableSource<List<Pair<String, Int>>> { observer ->
                    observer.onError(it)
                }
            }
            .lastElement()
            .flatMapObservable {
                ObservableSource<List<Pair<String, Int>>> { observer ->
                    observer.onNext(mapToSortedList(wordsMap))
                    observer.onComplete()
                }
            }
            .subscribeOn(Schedulers.io())
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

                } catch (e: Exception) {
                    e.printStackTrace()
                    emitter.onError(e)
                }
            }
    }

    fun getCachedData(): List<Pair<String, Int>> {
        return mapToSortedList(wordsMap)
    }

    fun setUrl(url:String){
        this.url = url
    }

}