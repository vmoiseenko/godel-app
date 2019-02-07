package com.godeltech.simpleapp.ui.history

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.godeltech.simpleapp.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HistoryActivityTest{


    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HistoryActivity::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun test() {

    }

    @Test
    fun testUiVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.historyView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        SystemClock.sleep(5000)
    }

    @Test
    fun testPressBack() {
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        assertEquals(mActivityTestRule.activity.isFinishing, true)
    }
}