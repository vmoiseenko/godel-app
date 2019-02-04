package com.godeltech.simpleapp.ui.splash

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.toPackage
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.ui.main.MainActivity
import com.godeltech.simpleapp.utils.WaitActivityIsResumedIdlingResource
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashActivityTest {

    @Rule
    @JvmField
    var mIntentsTestRule = IntentsTestRule(SplashActivity::class.java)

    @Before
    fun setUp() {
        Espresso.registerIdlingResources(WaitActivityIsResumedIdlingResource(MainActivity::class.java.name))
//        intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))
    }

    @After
    fun tearDown() {
        Intents.assertNoUnverifiedIntents()
    }

    @Test
    fun launchActivityTest() {
        val delay = mIntentsTestRule.activity.resources.getInteger(R.integer.splash_delay)
        assertTrue(delay == 5)
        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun checkResultTest() {
        val result = ActivityResult(Activity.RESULT_OK, Intent())
        intending(toPackage(MainActivity::class.java.name)).respondWith(result)
        intended(hasComponent(MainActivity::class.java.name))
    }
}