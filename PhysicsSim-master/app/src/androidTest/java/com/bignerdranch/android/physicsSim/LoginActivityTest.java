package com.bignerdranch.android.physicsSim;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.bignerdranch.android.physicsSim.activities.LoadSimActivity;
import com.bignerdranch.android.physicsSim.activities.LoginActivity;
import com.bignerdranch.android.physicsSim.activities.MainMenuActivity;
import com.bignerdranch.android.physicsSim.activities.NewSimActivity;
import com.bignerdranch.android.physicsSim.activities.UserSettingsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
        // Create a test user account prior to login attempt (Repeat creates will simply pass by).
        onView(withId(R.id.new_user_button)).perform(click());
        onView(withId(R.id.username)).perform(typeText("test"));
        onView(withId(R.id.password)).perform(typeText("testABCD"));
        onView(withId(R.id.password_confirm)).perform(typeText("testABCD"), closeSoftKeyboard());
        onView(withId(R.id.done_button)).perform(click());
        onView(withId(R.id.exit_button)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void loginValidAccount() {
        onView(withId(R.id.username_text)).perform(typeText("test"));
        onView(withId(R.id.password_text)).perform(typeText("testABCD"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        intended(hasComponent(MainMenuActivity.class.getName()));
    }

    @Test
    public void loginIncorrectPassword() {
        onView(withId(R.id.username_text)).perform(typeText("test"));
        onView(withId(R.id.password_text)).perform(typeText("testABC"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        // Check that username and password fields remain the same.
        onView(withId(R.id.username_text)).check(matches(withText("test")));
        onView(withId(R.id.password_text)).check(matches(withText("testABC")));
    }
}