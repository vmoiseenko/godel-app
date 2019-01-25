package com.godeltech.simpleapp.repository

import com.godeltech.simpleapp.network.MockNetworkService
import io.reactivex.functions.Consumer
import io.reactivex.observers.TestObserver
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DataRepositoryTest {

    lateinit var repository: DataRepository

    private lateinit var networkService: MockNetworkService

    var url = "https://tut.by"

    @Before
    fun setUp(){
        networkService = MockNetworkService()
        repository = DataRepository(networkService)
    }

    @Test
    fun testGetData(){
        val observer = TestObserver.create<ResponseBody>()
        repository.getTextFile(url).subscribe(observer)
        observer.assertNoErrors()
        observer.assertComplete()
        assertTrue(observer.hasSubscription())
    }

    @Test
    fun testGetData2(){
        val observer = TestObserver.create<ResponseBody>()
        repository.getTextFile(url).subscribe(observer)

        observer.assertOf(Consumer {
            println(it.toString()) })

        observer.assertError(Throwable("Error!!!"))
        observer.assertComplete()

        assertTrue(observer.hasSubscription())
    }


}