package com.godeltech.simpleapp.network

import com.godeltech.simpleapp.network.NetworkService
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.ResponseBody

class MockNetworkService : NetworkService {

    override fun downloadFileWithUrl(fileUrl: String): Observable<ResponseBody> {
        val content = "download file with ulr test test file file"
        val rb = ResponseBody.create(MediaType.parse("text"), content)
        return Observable.just(rb)
    }

}