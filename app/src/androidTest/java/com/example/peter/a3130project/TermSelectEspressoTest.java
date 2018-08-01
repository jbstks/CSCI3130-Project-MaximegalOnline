package com.example.peter.a3130project;

import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

public class TermSelectEspressoTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);
    
    @Test
    public void TestClickingWinter2018() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Winter 2018")));
    }

    @Test
    public void TestClickingSummer2018() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));
    }

    @Test
    public void TestClickingFall2018() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(2, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Fall 2018")));
    }
    @Test
    public void TestClickingWinter2019() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(3, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Winter 2019")));
    }
    @Test
    public void TestClickingSummer2019() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(4, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2019")));
    }
    @Test
    public void TestClickingFall2019() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(5, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Fall 2019")));
    }

}
