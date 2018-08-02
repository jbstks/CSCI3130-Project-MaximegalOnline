package com.example.peter.a3130project;

import android.os.SystemClock;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.view.View;


import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;


import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


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
     * A_attemptRegister
     * Attempts registration of course
     **/
    @Test
    public void A_attemptRegister() {
        SystemClock.sleep(1500);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        SystemClock.sleep(1500);
        onView(withId(R.id.registerButton)).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.sortByFacultySpinner)).perform(click());
        SystemClock.sleep(1500);
        onView(withText("Computer Science")).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.course_rv)).perform(actionOnItemAtPosition(0, click()));
        SystemClock.sleep(1500);
        onView(withId(R.id.sections_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.register_button)));
        SystemClock.sleep(1500);

    }


    /**
     * B_removeTest
     * Attempts removing a course from registration
     **/
    @Test
    public void B_removeTest() {


        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        SystemClock.sleep(1500);
        onView(withText("Computer Science I")).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.sections_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.drop_button)));
        SystemClock.sleep(1500);




    }


    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };


    }
}


