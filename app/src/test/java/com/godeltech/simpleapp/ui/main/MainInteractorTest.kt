package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.network.MockNetworkService
import com.godeltech.simpleapp.repository.DataRepository
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainInteractorTest {

    lateinit var dataRepository: DataRepository
    lateinit var interactor: MainInteractor

    @Before
    fun setUp() {
        dataRepository = DataRepository(MockNetworkService())
    }

    @Test
    fun requestData() {

        val observer = TestObserver.create<List<Pair<String, Int>>>()

        interactor = MainInteractor(dataRepository)
        interactor.setUrl("https://google.com")
        interactor.requestData().subscribe(observer)

        Assert.assertTrue(observer.hasSubscription())
        observer.assertNoErrors()

        observer.awaitTerminalEvent()

        observer.assertComplete()
    }

    @Test
    fun getCachedData() {
    }

}