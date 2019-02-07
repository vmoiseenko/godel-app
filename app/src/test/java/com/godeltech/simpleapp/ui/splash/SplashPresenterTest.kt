package com.godeltech.simpleapp.ui.splash

import io.github.plastix.rxschedulerrule.RxSchedulerRule
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit


class SplashPresenterTest {

    @get:Rule
    val rxSchRule = RxSchedulerRule()

    @Mock
    lateinit var view: SplashContract.View

    lateinit var presenter: SplashPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SplashPresenter(SplashDelay(1, TimeUnit.SECONDS))
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(view)
    }

    @Test
    fun testRunApp() {
        Mockito.verify(view).launchApp()
    }



}