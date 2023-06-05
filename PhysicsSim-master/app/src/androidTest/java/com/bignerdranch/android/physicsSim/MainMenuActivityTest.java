package com.bignerdranch.android.physicsSim;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.bignerdranch.android.physicsSim.activities.LoadSimActivity;
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
public class MainMenuActivityTest {

    @Rule
    public ActivityScenarioRule<MainMenuActivity> activityRule = new ActivityScenarioRule<>(MainMenuActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void newSimButton() {
        onView(withId(R.id.buttonNewSimulation)).perform(click());
        intended(hasComponent(NewSimActivity.class.getName()));
    }

    @Test
    public void loadSimButton() {
        onView(withId(R.id.buttonSimulationList)).perform(click());
        intended(hasComponent(LoadSimActivity.class.getName()));
    }

    @Test
    public void userSettingsButton() {
        onView(withId(R.id.buttonUserSettings)).perform(click());
        intended(hasComponent(UserSettingsActivity.class.getName()));
    }
}