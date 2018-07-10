package com.example.peter.a3130project;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

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
//        String username = "testing@test.com";
//        String password = "testing123";
//
//
//        onView(withId(R.id.et_email)).perform(typeText(username));
//        onView(withId(R.id.et_password)).perform(typeText(password));
//        Espresso.closeSoftKeyboard();
//
//
//        onView(withId(R.id.bt_signin)).perform(click());
//        SystemClock.sleep(1500);

        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Winter 2018")));

        SystemClock.sleep(1500);

        onView(withText("CSCI1110")).perform(click());
        SystemClock.sleep(1500);

        /*onView(withId(R.id.sections_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Computer Science 2")), click()));*/

//        onView(withId(R.id.sections_rv))
//                .check(matches(hasDescendant(withText("01"))))
//                        .perform(actionOnItem(withId(R.id.register_button), click()));

        onView(withId(R.id.sections_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.register_button)));
    }


    /**
     * @function B_removeTest
     *Attempts removing a course from registration
     *
     **/
    @Test
    public void B_removeTest() {assert(false);
//        String username = "testing@test.com";
//        String password = "testing123";
//
//
//        onView(withId(R.id.et_email)).perform(typeText(username));
//        onView(withId(R.id.et_password)).perform(typeText(password));
//        Espresso.closeSoftKeyboard();
//
//
//        onView(withId(R.id.bt_signin)).perform(click());
//        SystemClock.sleep(1500);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        onView(withText("Drop")).perform(click());
        SystemClock.sleep(1500);

        onView(withId(R.id.crnText)).perform(typeText("32243"));
        SystemClock.sleep(1500);

        onView(withId(R.id.dropButton)).perform(click());







        
        

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
