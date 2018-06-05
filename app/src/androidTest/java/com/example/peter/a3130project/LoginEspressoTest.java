package com.example.peter.a3130project;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityrule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testCorrectLogin_Button() {
	/*
	  Tests login using a correct login with the button.
	 */
        String username = "testing@test.com";
        String password = "testing";

        onView(withId(R.id.email)).perform(typeText(username));
	    onView(withId(R.id.password)).perform(typeText(password));

	    onView(withId(R.id.email_sign_in_button)).perform(click());

	//Wait until the view changes.

    }


}
