package ua.dp.rename.dniprostreets

import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ua.dp.rename.dniprostreets.view.AboutActivity
import ua.dp.rename.dniprostreets.view.MainActivity

class InstrumentedTest {

   @get:Rule val mainActivityRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

   @Before
   fun setup() {
      InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MockApp
   }

   @Test
   fun test_MainActivityToolbarTitle() {
      val title = ApplicationProvider.getApplicationContext<Context>().getString(R.string.app_name)
      onView(isAssignableFrom(Toolbar::class.java)).check(matches(hasDescendant(withText(title))))
   }

   @Test
   fun test_AboutActivityLaunched() {
      Intents.init()

      onView(withId(R.id.action_about)).perform(click())

      intended(hasComponent(AboutActivity::class.java.name))
   }
}
