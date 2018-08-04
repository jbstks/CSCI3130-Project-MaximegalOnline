package com.example.peter.a3130project;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SubjectSortEspressoTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);

    @Test
    public void switchSubjectTest() {
        SystemClock.sleep(1500);
        onView(withId(R.id.rv)).perform(actionOnItemAtPosition(1, click()));
        SystemClock.sleep(1500);
        onView(withText("REGISTER")).perform(click());
                                                      

        //onView(withId(R.id.sections_rv)).perform(matches(hasDescendant(withText("REGISTER"))),click());
        SystemClock.sleep(1500);
        // onView(withId(R.id.course_rv))
        //     .check(RecyclerViewActions.actionOnItem(
        //                                             hasDescendant(withText("Business for kids"))));
        onView(withId(R.id.course_cv))
                .check(matches(hasDescendant(withText("BUSI 1000"))));

        assert(false);
    }

}
