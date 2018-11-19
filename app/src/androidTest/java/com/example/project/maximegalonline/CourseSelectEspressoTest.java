package com.example.project.maximegalonline;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import com.csci3130.project.maximegalonline.TermActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
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
    public void TestWinter2018CS2() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Winter 2018")));
        SystemClock.sleep(1500);
        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Computer Science 2")), click()));
        onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI1110")));
        onView(withId(R.id.courseInfo_name)).check(matches(withText("Computer Science 2")));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("01"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("32243"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("Sampali Srini"))));

        SystemClock.sleep(1500);
    }

    @Test
    public void TestSummer2018SEid() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));
        SystemClock.sleep(1500);

        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("PlaceHolder 1")), click()));
        onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI3111")));
        onView(withId(R.id.courseInfo_name)).check(matches(withText("PlaceHolder 1")));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("01"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("42400"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("Norbert Zeh"))));

        SystemClock.sleep(1500);
    }

    @Test
    public void TestSummer2018Algorithms() {
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Summer 2018")));
        SystemClock.sleep(1500);

        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Algorithms")), click()));
        onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI3110")));
        onView(withId(R.id.courseInfo_name)).check(matches(withText("Algorithms")));
        onView(withId(R.id.courseInfo_description)).check(matches(withText("This course covers techniques for the design and analysis of efficient algorithms and data structures.")));
        onView(withId(R.id.courseInfo_prerequisites)).check(matches(withText("CSCI2110\nCSCI2112\n")));

        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("01"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("42343"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("Norbert Zeh"))));

        SystemClock.sleep(1500);
    }

}
