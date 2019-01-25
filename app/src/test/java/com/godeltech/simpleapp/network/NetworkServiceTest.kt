package com.godeltech.simpleapp.network

import org.junit.Test

class NetworkServiceTest {

    @Test
    fun requestData(){
        val api = MockNetworkService()
        api.downloadFileWithUrl("")
    }

}