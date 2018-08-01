package com.example.peter.a3130project;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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


        SystemClock.sleep(1500);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));

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


//    /**
//     *  fullTest
//     *Attempts registering for a course that it's at full capacity
//     *
//     **/
//    @Test
//    public void C_fullTest() {
//
//        SystemClock.sleep(1500);
//        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
//
//        SystemClock.sleep(1500);
//        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
//        onView(withText("Drop")).perform(click());
//        SystemClock.sleep(1500);
//
//        onView(withId(R.id.crnText)).perform(typeText("42343"));
//
//
//
//
//    }
}

