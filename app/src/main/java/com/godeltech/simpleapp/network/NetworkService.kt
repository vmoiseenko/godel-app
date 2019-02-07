package com.godeltech.simpleapp.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface NetworkService {

    @Streaming
    @GET
    fun downloadFileWithUrl(@Url fileUrl: String): Observable<ResponseBody>

}