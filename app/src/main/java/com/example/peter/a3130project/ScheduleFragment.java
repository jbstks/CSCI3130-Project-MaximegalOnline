package com.example.peter.a3130project;

import com.example.peter.a3130project.course.*;
import android.graphics.Color;
import android.graphics.Typeface;
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

/**
 * A fragment containing the user's schedule.
 *
 * @author Joanna Bistekos
 * @author Bradley Garagan
 * @author Peter Lee
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

        final ArrayList<String> days = new ArrayList<String>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));

        final String[] colors = { "#40f44336", "#40ffc107", "#404caf50", "#402196f3" };
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
            TextView classtv = new TextView(getActivity().getApplicationContext());
            TextView locationtv = new TextView(getActivity().getApplicationContext());

            int duration = 60 * ((e.getEnd() / 100) - (e.getStart() / 100)) + (e.getEnd() % 100 - e.getStart() % 100);
            int before =  60 * ((e.getStart() / 100) - (time / 100)) + (e.getStart() % 100 - time % 100);

            duration = ((duration + 25) / 30) * 30;
            before = ((before + 25) / 30) * 30;

            final float scale = getResources().getDisplayMetrics().density;
            int blockSize = (int) (50 * scale + 0.5f);
            int height = blockSize * duration / 60;
            int margin = blockSize * before / 60;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

            LinearLayout scheduleEntry = new LinearLayout(getActivity().getApplicationContext());
            scheduleEntry.setOrientation(LinearLayout.VERTICAL);
            lp.setMargins(0, margin, 0, 0);
            scheduleEntry.setPadding(16,16,16,0);
            scheduleEntry.setLayoutParams(lp);
            scheduleEntry.setBackgroundColor(e.getColor());

            // Get darker color
            float[] hsv = new float[3];
            Color.colorToHSV(e.getColor(), hsv);
            hsv[2] *= 0.8f;
            int textColor = Color.HSVToColor(hsv);

            classtv.setTextColor(textColor);
            classtv.setText(e.getName());
            classtv.setTextSize(14);
            classtv.setTypeface(null, Typeface.BOLD);

            locationtv.setTextColor(textColor);
            locationtv.setText(e.getLocation());
            locationtv.setTextSize(13);

            scheduleEntry.addView(classtv);
            scheduleEntry.addView(locationtv);

            Log.d("sched", "adding view with height " + height);

            tab.addView(scheduleEntry);

            time = e.getEnd();
        }
    }
}