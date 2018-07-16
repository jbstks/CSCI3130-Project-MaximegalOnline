package com.example.peter.a3130project;

import com.example.peter.a3130project.course.*;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A fragment containing the user's schedule.
 *
 * @author Joanna Bistekos
 * @author Bradley Garagan
 */
public class ScheduleFragment extends Fragment {

    private View view;
    private int selectedDay = 0;
    private List<CourseSection> courses;

    /**
     * Default constructor for ScheduleFragment
     */
    public ScheduleFragment() {
    }

    /**
     * Things to be done on activity creation
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return inflated schedule view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_schedule, container, false);
        final ScheduleFragment f = this;

        TabLayout tabs = view.findViewById(R.id.schedule_tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedDay = tab.getPosition();
                f.update(f.courses);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    /**
     * Update courses if they are changed in the database
     *
     * @param registeredCourses list of available courses (of object CourseSections)
     */
    public void update(List<CourseSection> registeredCourses) {

        this.courses = registeredCourses;

        if (view == null)
            return;

        final LinearLayout tab = view.findViewById(R.id.schedule_tab);

        final ArrayList<String> days = new ArrayList<String>(Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday"));

        final String[] colors = { "#ffe8e8", "#f0f0da", "#d3eabb", "#a9c4a0" };
        int colorIndex = 0;

        final ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        tab.removeAllViews();

        for (CourseSection c : registeredCourses) {
            int color = Color.parseColor(colors[colorIndex++ % colors.length]);
            for (CourseTime t : c.getcourseTimeList()) {
                int i = days.indexOf(t.getday());
                int start = Integer.parseInt(t.getstartTime());
                int end = Integer.parseInt(t.getendTime());
                if (i == selectedDay) {
                    schedule.add(new ScheduleEntry(c.getcourse().getcode(), start, end, t.getlocation(), color));
                }
            }
        }


        Collections.sort(schedule);

        int time = 805;

        for (ScheduleEntry e : schedule) {
            TextView tv = new TextView(getActivity().getApplicationContext());

            int duration = 60 * ((e.getEnd() / 100) - (e.getStart() / 100)) + (e.getEnd() % 100 - e.getStart() % 100);
            int before =  60 * ((e.getStart() / 100) - (time / 100)) + (e.getStart() % 100 - time % 100);

            duration = ((duration + 25) / 30) * 30;
            before = ((before + 25) / 30) * 30;

            final float scale = getResources().getDisplayMetrics().density;
            int blockSize = (int) (50 * scale + 0.5f);
            int height = blockSize * duration / 60;
            int margin = blockSize * before / 60;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height);
            LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, margin);

            View v = new View(getActivity().getApplicationContext());
            v.setLayoutParams(mlp);
            tab.addView(v);

            tv.setBackgroundColor(e.getColor());
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setText(e.getName() + "\n" + e.getLocation());
            Log.d("sched", "adding view with height " + height);
            tab.addView(tv);
            tv.setLayoutParams(lp);

            time = e.getEnd();
        }
    }
}