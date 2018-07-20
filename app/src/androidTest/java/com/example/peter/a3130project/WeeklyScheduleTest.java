package com.example.peter.a3130project;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ScrollingTabContainerView;
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
import static org.hamcrest.core.StringContains.containsString;

public class WeeklyScheduleTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);

    @Test
    public void TestWeeklySchedule() {
        SystemClock.sleep(2000);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        SystemClock.sleep(2000);
        onView(withText(R.string.tab_text_2)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.schedule_tab)).check(matches(hasDescendant(withText(containsString("CSCI3110")))));
        onView(withId(R.id.schedule_tab)).check(matches(hasDescendant(withText(containsString("CSCI3111")))));
        onView(withText("T")).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.schedule_tab)).check(matches(hasDescendant(withText(containsString("CSCI3110")))));
    }

}
