/*
 *  Copyright (C) 2020 Constantine Theodoridis
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package constantine.theodoridis.app.sunshine.ui.weatherforecastdetails

import android.app.Activity.RESULT_OK
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import constantine.theodoridis.app.sunshine.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class WeatherForecastDetailsActivityTest {
    private val hiltRule = HiltAndroidRule(this)
    private val intentsRule = object : IntentsTestRule<WeatherForecastDetailsActivity>(WeatherForecastDetailsActivity::class.java) {
        override fun getActivityIntent(): Intent {
            return Intent(getInstrumentation().targetContext, WeatherForecastDetailsActivity::class.java).apply {
                data = Uri.parse("content://constantine.theodoridis.app.sunshine/weather/94043/1584050400000")
            }
        }
    }

    @get:Rule
    val rules = RuleChain.outerRule(hiltRule).around(intentsRule)!!

    // TODO: Customize test to run on VERSION_CODE_LOLLIPOP and above to test flag
    // TODO: Remove mForecast null check in production code when Loader is replaced by ViewModel
    @Test
    fun shouldShareWeatherForecast_WhenShareOptionIsClicked() {
        intending(hasAction(ACTION_SEND)).respondWith(ActivityResult(RESULT_OK, null))
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)

        onView(withText(R.string.action_share)).perform(click())

        intended(allOf(
                hasAction(equalTo(ACTION_SEND)),
                hasFlag(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
                hasType("text/plain"),
                hasExtra(EXTRA_TEXT, "#SunshineApp")
        ))
    }
}