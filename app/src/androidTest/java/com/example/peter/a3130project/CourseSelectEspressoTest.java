package com.example.peter.a3130project;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class CourseSelectEspressoTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);

    @Test
    public void TestWinter2018FirstCourse() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Winter 2018")));
        SystemClock.sleep(1500);
        onView(withId(R.id.course_rv)).perform(actionOnItemAtPosition(0, click()));
        SystemClock.sleep(1500);
    }

    @Test
    public void TestWinter2018CS1() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Winter 2018")));
        SystemClock.sleep(1500);
        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Computer Science I")), click()));
        SystemClock.sleep(1500);
    }

    @Test
    public void TestSummer2018SEid() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));
        SystemClock.sleep(1500);
        /*onView(withId(R.id.course_rv))
                .check(matches(hasDescendant(withText("42665")))).perform(click());*/
        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("42665")), click()));
        onView(withId(R.id.section_crn)).check(matches(withText("42665")));
        onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI3130")));
        onView(withId(R.id.courseInfo_name)).check(matches(withText("Software Engineering")));
        SystemClock.sleep(1500);
    }

    @Test
    public void TestSummer2018SEName() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));
        SystemClock.sleep(1500);
        /*onView(withId(R.id.course_rv))
                .check(matches(hasDescendant(withText("42665")))).perform(click());*/
        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Algorithms")), click()));
        //onView(withId(R.id.section_crn)).check(matches(withText("42665")));
        //onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI3130")));
        //onView(withId(R.id.courseInfo_name)).check(matches(withText("Software Engineering")));
        SystemClock.sleep(1500);
    }

    @Test
    public void TestSummer2018OSid() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));
        SystemClock.sleep(1500);
        /*onView(withId(R.id.course_rv))
                .check(matches(hasDescendant(withText("42665")))).perform(click());*/
        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("42784")), click()));
        onView(withId(R.id.section_crn)).check(matches(withText("42784")));
        onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI3120")));
        onView(withId(R.id.courseInfo_name)).check(matches(withText("Operating Systems")));
        SystemClock.sleep(1500);
    }

    @Test
    public void TestSummer2018OSName() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));
        SystemClock.sleep(1500);
        /*onView(withId(R.id.course_rv))
                .check(matches(hasDescendant(withText("42665")))).perform(click());*/
        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Operating Systems")), click()));
        onView(withId(R.id.section_crn)).check(matches(withText("42784")));
        onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI3120")));
        onView(withId(R.id.courseInfo_name)).check(matches(withText("Operating Systems")));
        SystemClock.sleep(1500);
    }
}
