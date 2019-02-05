package com.godeltech.simpleapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.utils.RecyclerViewMatcher
import com.godeltech.simpleapp.utils.RecyclerViewMatcher.Companion.withItemCount
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
    }

    @Test
    fun uiTextIsCorrect(){
        onView(withId(R.id.actionButton)).check(matches(withText(getString(R.string.main_action_button_text))))
        onView(withId(R.id.urlField)).check(matches(withHint(getString(R.string.main_url_hint))))
    }

    @Test
    fun mainActivityTestTypedValidUrl() {

        onView(withId(R.id.urlField)).check(matches(isEnabled()))
        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))

        onView(withId(R.id.urlField)).perform(typeText("https://godel.tech"), closeSoftKeyboard())
        onView(withId(R.id.actionButton)).check(matches(isEnabled()))

    }

    @Test
    fun mainActivityTestTypedInvalidUrl() {

        onView(withId(R.id.urlField)).check(matches(isEnabled()))
        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))

        onView(withId(R.id.urlField)).perform(typeText("https://godel"), closeSoftKeyboard())

        onView(withId(R.id.actionButton)).check(matches(not(isEnabled())))

    }

    @Test
    fun mainActivityTestValidResult() {

        onView(withId(R.id.urlField)).perform(typeText("https://godel.tech"), closeSoftKeyboard())
        onView(withId(R.id.actionButton)).perform(click())

        onView(withId(R.id.resultListRecycler)).check(matches(isDisplayed()))

        onView(withId(R.id.resultListRecycler)).check(matches(withItemCount(5)))
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("file"))))
    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.resultListRecycler)
    }

    private fun getString(id: Int): String{
        return mActivityTestRule.activity.getString(id)
    }

}
