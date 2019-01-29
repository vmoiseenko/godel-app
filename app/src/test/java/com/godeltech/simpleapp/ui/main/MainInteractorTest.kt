package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.repository.DataRepository
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.*
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainInteractorTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var dataRepository: DataRepository

    lateinit var interactor: MainInteractor

    val validUrl = "https://google.com"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = MainInteractor(dataRepository)
        interactor.setUrl(validUrl)
    }

    @After
    fun terDown() {
        Mockito.verifyNoMoreInteractions(dataRepository)
    }

    @Test
    fun testDataRequest() {

        val rb = ResponseBody.create(MediaType.parse("text"), "content")
        Mockito.`when`(dataRepository.getTextFile(Mockito.anyString())).thenReturn(Observable.just(rb))

        interactor.requestData()

        verify(dataRepository).getTextFile(Mockito.anyString())

    }

    @Test
    fun testDataRequestFailed() {

        Mockito.`when`(dataRepository.getTextFile(validUrl)).thenReturn(Observable.error(Throwable("mistake")))
        val observer = interactor.requestData()
        observer.test().assertErrorMessage("mistake")

    }

    @Test
    fun testCachedData() {

        val content = "download file with ulr test test file file"
        val rb = ResponseBody.create(MediaType.parse("text"), content)
        Mockito.`when`(dataRepository.getTextFile(Mockito.anyString())).thenReturn(Observable.just(rb))

        val preparedListResult = listOf(
            Pair("file", 3),
            Pair("test", 2),
            Pair("with", 1),
            Pair("download", 1),
            Pair("ulr", 1)
        )

        interactor.requestData().subscribe()

        verify(dataRepository).getTextFile(Mockito.anyString())
        assertArrayEquals(arrayOf(interactor.getCachedData()), arrayOf(preparedListResult))
        assertNotEquals(arrayOf(interactor.getCachedData()), arrayOf(listOf(Pair("test", 1))))

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


}