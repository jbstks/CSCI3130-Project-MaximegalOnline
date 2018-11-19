package com.example.project.maximegalonline;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.csci3130.project.maximegalonline.TermActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;

public class CourseCapacityTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);

    @Test
    public void TestCourseCapacityIsDisplayed() {
        SystemClock.sleep(2000);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));
        SystemClock.sleep(2000);
        onView(withText("REGISTER")).perform(click());
        SystemClock.sleep(2000);
        onView(withText(containsString("Icecream"))).perform(click());
        onView(withText("CAPACITY")).check(matches(isDisplayed()));
    }

    @Test
    public void TestCantRegisterForFullCourseSection() {
        SystemClock.sleep(2000);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));
        SystemClock.sleep(2000);
        onView(withText("REGISTER")).perform(click());
        SystemClock.sleep(2000);
        onView(withText(containsString("Icecream"))).perform(click());
        SystemClock.sleep(2000);
        onView(withText("REGISTER")).perform(click());
        SystemClock.sleep(1500);
        onView(withText(containsString("Course section is full."))).inRoot(withDecorView(not(is(activityrule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

}
