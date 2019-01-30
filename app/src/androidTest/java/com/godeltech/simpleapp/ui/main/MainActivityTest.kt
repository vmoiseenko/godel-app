package com.godeltech.simpleapp.ui.main


import android.content.Intent
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.di.component.DaggerAppComponent
import com.godeltech.simpleapp.di.module.*
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {

        val instrumentation= InstrumentationRegistry.getInstrumentation()

        val app = instrumentation.targetContext.applicationContext as BaseApplication

        val component = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .networkModule(MockNetworkModule())
            .dataModule(DataModule())
            .build()

        app.component = component

//        val intent = Intent(InstrumentationRegistry.getInstrumentation()
//            .targetContext, MainActivity::class.java)

//        mActivityTestRule.launchActivity(intent)
    }

    @Test
    fun mainActivityTestTypedValidUrl() {

        onView(withId(R.id.urlField)).check(matches(isEnabled()))
        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))

        onView(withId(R.id.urlField)).perform(typeText("https://tut.by"), closeSoftKeyboard())
        onView(withId(R.id.actionButton)).perform(click())

        onView(withId(R.id.urlField)).check(matches(not(isEnabled())))
        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress)).check(matches(isDisplayed()))

    }

    @Test
    fun mainActivityTestTypedInvalidUrl() {

        onView(withId(R.id.urlField)).check(matches(isEnabled()))
        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))

        onView(withId(R.id.urlField)).perform(typeText("https://tut"), closeSoftKeyboard())

        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))

    }

    @Test
    fun mainActivityTestValidResult() {

        onView(withId(R.id.urlField)).perform(typeText("https://tut.by"), closeSoftKeyboard())
        onView(withId(R.id.actionButton)).perform(click())

        onView(withId(R.id.urlField)).check(matches(not(isEnabled())))
        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress)).check(matches(isDisplayed()))

        SystemClock.sleep(5000)

        onView(withId(R.id.resultListRecycler)).perform(RecyclerViewActions.scrollToPosition<>(50))


    }

}
