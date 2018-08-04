package com.example.peter.a3130project;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class LoginEspressoTest {
    private String error_field_required = "This field is required";
    private String error_invalid_email = "This email address is invalid [must be more than 8 chars]";
    private String error_invalid_password = "This password is too short [must be more than 8 chars]";

    @Rule
    public ActivityTestRule<LoginActivity> activityrule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void A_testMalformedLogin_Button() {
        Espresso.closeSoftKeyboard();

	onView(withId(R.id.bt_signin)).perform(click());
	ViewInteraction et_field = onView(withId(R.id.et_email));

	et_field.check(matches(hasErrorText(error_field_required)));

    }



    @Test
    public void B_testShortUser() {
	String username = "hi@hi.c";
        String password = "tesdfting";
	
        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
	    onView(withId(R.id.bt_signin)).perform(click());
        //ViewInteraction et_field = onView(withId(R.id.til_email));
        //et_field.check(matches(hasErrorText(error_invalid_email)));
        //SystemClock.sleep(1500);
        //onView(withText(error_invalid_email)).check(matches(isDisplayed()));
        //SystemClock.sleep(1500);
        //onView(withText(error_invalid_email)).check(matches(isDisplayed()));

        ViewInteraction et_field = onView(withId(R.id.et_email));
        et_field.check(matches(hasErrorText(error_invalid_email)));
    }

    @Test
    public void C_testShortPW() {
	String username = "ac@tt.comasdg";
        String password = "test";
	
        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
	    onView(withId(R.id.bt_signin)).perform(click());

        ViewInteraction et_field = onView(withId(R.id.et_password));
        et_field.check(matches(hasErrorText(error_invalid_password)));


    }

    @Test
    public void D_testIncorrectLogin_Button() {

	  //Tests login using a correct login with the button.

        String username = "ac@tt.com";
        String password = "tesdfting";

        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
	    onView(withId(R.id.bt_signin)).perform(click());

	    //Wait until the view changes.

        onView(withId(R.id.et_email)).check(matches(isDisplayed()));
    }

    @Test
    public void E_testCorrectLogin_Button() {

	/*
	  Tests login using a correct login with the button.
	 */
        Intent result = new Intent();

        String username = "testing@test.com";
        String password = "testing123";


        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();


	    onView(withId(R.id.bt_signin)).perform(click());
        SystemClock.sleep(1500);

        //Intents.release();
	//Wait until the view changes.
        //intended(toPackage("com.example.peter.a3130.MainActivity"));
	    //intended(hasComponent(MainActivity.class.getName()));
        //onView(withId(R.menu.menu_main2)).check(matches(isDisplayed()));
        onView(withId(R.id.rv)).check(matches(isDisplayed()));
    }


}
