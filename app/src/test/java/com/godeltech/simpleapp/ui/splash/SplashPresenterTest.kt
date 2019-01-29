package com.godeltech.simpleapp.ui.splash

import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SplashPresenterTest {

    @get:Rule
    val rxSchRule = RxSchedulerRule()

    @Mock
    lateinit var view: SplashContract.View

    lateinit var presenter: SplashPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SplashPresenter(0)
        presenter.attachView(view)
    }

    @Test
    fun detachView() {
        presenter.detachView()
        assertNull(presenter.view)
    }

    @Test
    fun testRunApp() {
        presenter.runApp()
        Mockito.verify(view).launchApp()
    }

//    @Test
//    fun testSubscriber() {
//        val observer = TestObserver.create<Any>()
//        observer.onSubscribe(presenter.getSubscriber())
//        observer.assertComplete()
//    }
}