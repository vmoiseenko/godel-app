package com.godeltech.simpleapp.ui.main

import android.view.View
import android.widget.TextView
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
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
    fun uiTextIsCorrect() {
        onView(withId(R.id.actionButton)).check(matches(withText(getString(R.string.main_action_button_text))))
        onView(withId(R.id.urlField)).check(matches(withTextInputHint(getString(R.string.main_url_hint))))
        onView(withId(R.id.textInputUrl)).check(matches(withTextInputHint(getString(R.string.main_url_label))))
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

    @Test
    fun mainActivityTestValidResultAndLaunchHistoryScreen() {

        onView(withId(R.id.urlField)).perform(typeText("https://godel.tech"), closeSoftKeyboard())
        onView(withId(R.id.actionButton)).perform(click())

        onView(withId(R.id.resultListRecycler)).check(matches(isDisplayed()))

        onView(withId(R.id.resultListRecycler)).check(matches(withItemCount(5)))
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("file"))))

        onView(withId(R.id.historyMenuItem)).perform(click())
    }

    private fun withTextInputHint(expectedText: String): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                when (view) {
                    is TextInputLayout -> {
                        val hint = view.hint ?: return false
                        return expectedText == hint.toString()
                    }
                    is TextInputEditText -> {
                        return try {
                            val f = TextView::class.java.getDeclaredField("mHint")
                            f.isAccessible = true
                            val hint = f.get(view) as String
                            expectedText == hint
                        } catch (e: Exception) {
                            false
                        }
                    }
                    else -> return false
                }
            }

            override fun describeTo(description: Description?) {}
        }
    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.resultListRecycler)
    }

    private fun getString(id: Int): String {
        return mActivityTestRule.activity.getString(id)
    }

}
