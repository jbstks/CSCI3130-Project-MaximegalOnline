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

public class SectionEspressoTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);

    @Test
    public void TestSectionList() {

        SystemClock.sleep(1500);

        //onRecyclerItemView(R.id.item_title, withText("Test"),  withId(R.id.item_content))
        //        .matches(check(withText("Test Content")));

        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));

        SystemClock.sleep(1500);

        onView(withId(R.id.course_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Algorithms")), click()));

        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("42343"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("42344"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("Norbert Zeh"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("Meng He"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("01"))));
        onView(withId(R.id.sections_rv))
                .check(matches(hasDescendant(withText("02"))));
        //onView(withId(R.id.courseInfo_id)).check(matches(withText("42665")));
        //onView(withId(R.id.courseInfo_code)).check(matches(withText("CSCI3130")));
        //onView(withId(R.id.courseInfo_name)).check(matches(withText("Software Engineering")));
        SystemClock.sleep(1500);

    }


}