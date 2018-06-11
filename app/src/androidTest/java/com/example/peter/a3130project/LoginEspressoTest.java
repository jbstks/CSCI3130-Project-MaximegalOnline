package com.example.peter.a3130project;

import android.support.test.espresso.Espresso;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class LoginEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityrule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void A_testMalformedLogin_Button() {
        Espresso.closeSoftKeyboard();

	onView(withId(R.id.bt_signin)).perform(click());
	
	editText.check(matches(hasErrorText(R.strings.badlengthlogin)));
    }



    @Test
    public void B_testShortUser() {
	String username = "hi@hi.c";
        String password = "tesdfting";
	
        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
	    onView(withId(R.id.bt_signin)).perform(click());
	
	editText.check(matches(hasErrorText(R.strings.badlengthlogin)));

    }

    @Test
    public void C_testShortPW() {
	String username = "ac@tt.comasdg";
        String password = "test";
	
        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
	    onView(withId(R.id.bt_signin)).perform(click());
	

	
	editText.check(matches(hasErrorText(R.strings.badlengthlogin)));

    }

    @Test
    public void D_testIncorrectLogin_Button() {
		/*
	  Tests login using a correct login with the button.
	 */
        String username = "ac@tt.com";
        String password = "tesdfting";

        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
	    onView(withId(R.id.bt_signin)).perform(click());


	//Wait until the view changes.

	intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void E_testCorrectLogin_Button() {

	/*
	  Tests login using a correct login with the button.
	 */
        String username = "testing@test.com";
        String password = "testing123";


        onView(withId(R.id.et_email)).perform(typeText(username));
	    onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
	    onView(withId(R.id.bt_signin)).perform(click());


	//Wait until the view changes.
	    intended(hasComponent(MainActivity.class.getName()));

    }


}
