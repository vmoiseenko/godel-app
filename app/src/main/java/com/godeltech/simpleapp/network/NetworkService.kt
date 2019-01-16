package com.godeltech.simpleapp.network

import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface NetworkService {

    @Streaming
    @GET
    fun downloadFileWithUrl(@Url fileUrl: String): Observable<ResponseBody>

    companion object Factory {
        fun create(): NetworkService {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://google.com/")
                .build()

            return retrofit.create(NetworkService::class.java)
        }
    }

}