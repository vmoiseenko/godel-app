package com.godeltech.simpleapp.ui.splash

import io.github.plastix.rxschedulerrule.RxSchedulerRule
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.toPackage
import android.support.test.espresso.intent.rule.IntentsTestRule
import org.hamcrest.CoreMatchers.allOf

class SplashPresenterTest {

    @get:Rule
    val rxSchRule = RxSchedulerRule()

    @get:Rule
    val testIntent = IntentsTestRule(SplashActivity::class.java)

    @Mock
    lateinit var view: SplashContract.View

    lateinit var presenter: SplashPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SplashPresenter(SplashDelay(1, TimeUnit.SECONDS))
        presenter.attachView(view)
    }

    @Test
    fun testRunApp() {
        Mockito.verify(view).launchApp()
    }

    @Test
    fun testRunIntent() {
        intended(allOf(toPackage(SplashActivity::class.java.name)))
    }

}