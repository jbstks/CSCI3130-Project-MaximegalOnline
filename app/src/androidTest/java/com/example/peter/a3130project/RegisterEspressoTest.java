package com.example.peter.a3130project;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.VisibleForTesting;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;


import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

/**
 * RegisterEspressoTest
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
     *  A_attemptRegister
     *Attempts registration of course 
     *
     **/
    @Test
    public void A_attemptRegister() {

        SystemClock.sleep(2500);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));

        SystemClock.sleep(1500);
        
        onView(withText("CSCI3110")).perform(click());
        SystemClock.sleep(1500);


        onView(allOf(withId(R.id.register_button), isDisplayed())).perform(click());
    }


    /**
     *  B_removeTest
     *Attempts removing a course from registration
     *
     **/
    @Test
    public void B_removeTest() {

        SystemClock.sleep(1500);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));

        SystemClock.sleep(1500);
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Drop")).perform(click());
        SystemClock.sleep(1500);

        onView(withId(R.id.crnText)).perform(typeText("42343"));

        
        

    }
}
