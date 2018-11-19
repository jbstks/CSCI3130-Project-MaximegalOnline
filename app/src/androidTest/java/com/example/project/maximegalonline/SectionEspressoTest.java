package com.example.project.maximegalonline;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.csci3130.project.maximegalonline.TermActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SectionEspressoTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);

    @Test
    public void TestSectionList() {

        SystemClock.sleep(1500);
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

        SystemClock.sleep(1500);

    }


}
