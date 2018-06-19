package com.example.peter.a3130project;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;


@RunWith(AndroidJUnit4.class)
public class LogOutEspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> activityrule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testLogOut() {
        Espresso.closeSoftKeyboard();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        //onView(withId(R.menu.menu_main2).;
        SystemClock.sleep(1500);
        //onView(withContentDescription(R.string.logout_name)).perform(click());
        //onView(withId(R.id.action_settings)).perform(click());
        onView(withText("Log Out")).perform(click());
        ViewInteraction bla = onView(withId(R.id.bt_signin));

        SystemClock.sleep(1500);

        bla.check(matches(isDisplayed()));



    }






}
