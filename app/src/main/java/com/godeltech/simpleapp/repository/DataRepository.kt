package com.godeltech.simpleapp.repository

import com.godeltech.simpleapp.network.NetworkService
import io.reactivex.Observable
import okhttp3.ResponseBody
import javax.inject.Inject

class DataRepository @Inject constructor (private val networkService: NetworkService) {

    fun getTextFile(url: String): Observable<ResponseBody> {
        return networkService.downloadFileWithUrl(url)
    }

}