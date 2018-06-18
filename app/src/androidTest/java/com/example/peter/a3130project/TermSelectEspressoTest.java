package com.example.peter.a3130project;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class TermSelectEspressoTest {

    @Rule
    public ActivityTestRule<TermActivity> activityrule =
            new ActivityTestRule<>(TermActivity.class);

    // Right now this fails as there is multiple cards with the same names
    // This is due to the use of the RecyclerView
    @Test
    public void TestClickingWinter2018() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.term_semester)).check(matches(withText("Winter")));
        onView(withId(R.id.term_year)).check(matches(withText("2018")));

    }
}
