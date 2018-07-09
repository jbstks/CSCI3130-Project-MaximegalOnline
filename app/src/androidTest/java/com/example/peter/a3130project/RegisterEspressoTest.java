package com.example.peter.a3130project;

import android.content.Intent;
import android.os.SystemClock;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @class RegisterEspressoTest
 *
 * Tests registration and removing courses
 **/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class RegisterEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityrule =
            new ActivityTestRule<>(LoginActivity.class);

    /**
     * @function A_attemptRegister
     *Attempts registration of course 
     *
     **/
    @Test
    public void A_attemptRegister() {
        String username = "testing@test.com";
        String password = "testing123";


        onView(withId(R.id.et_email)).perform(typeText(username));
        onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();


        onView(withId(R.id.bt_signin)).perform(click());
        SystemClock.sleep(1500);

        onView(withText("CSCI3110")).perform(click());
        SystemClock.sleep(1500);

        onView(withText("Register")).perform(click());

    }


    /**
     * @function B_removeTest
     *Attempts removing a course from registration
     *
     **/
    @Test
    public void B_removeTest() {assert(false);
        String username = "testing@test.com";
        String password = "testing123";


        onView(withId(R.id.et_email)).perform(typeText(username));
        onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();


        onView(withId(R.id.bt_signin)).perform(click());
        SystemClock.sleep(1500);

        onView(withText("CSCI3110")).perform(click());
        SystemClock.sleep(1500);

        onView(withText("Register")).perform(click());

    }
}
