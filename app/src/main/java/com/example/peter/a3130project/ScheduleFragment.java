package com.example.peter.a3130project;

import android.graphics.Color;
import android.os.Bundle;
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
 */
public class ScheduleFragment extends Fragment {

    private View view;

    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.schedule, container, false);

        return view;
    }

    public void update(List<Course> courses) {

        if (view == null)
            return;

        final LinearLayout[] dayViews = {
                view.findViewById(R.id.monday),
                view.findViewById(R.id.tuesday),
                view.findViewById(R.id.wednesday),
                view.findViewById(R.id.thursday),
                view.findViewById(R.id.friday)
        };

        final ArrayList<String> days = new ArrayList<String>(Arrays.asList("mon", "tue", "wed", "thu", "fri"));

        final String[] colors = { "#ffe8e8", "#f0f0da", "#d3eabb", "#a9c4a0" };
        int colorIndex = 0;

        final ArrayList<ArrayList<ScheduleEntry>> schedule = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            schedule.add(new ArrayList<ScheduleEntry>());
            dayViews[i].removeViews(1, dayViews[i].getChildCount() - 1);
        }

        for (Course c : courses) {
            int color = Color.parseColor(colors[colorIndex++ % colors.length]);
            for (CourseTime t : c.times) {
                int i = days.indexOf(t.day);
                int start = Integer.parseInt(t.startTime);
                int end = Integer.parseInt(t.endTime);
                schedule.get(i).add(new ScheduleEntry(c.code, start, end, t.location, color));
            }
        }

        for (int i = 0; i < 5; ++i) {
            ArrayList<ScheduleEntry> day = schedule.get(i);
            Collections.sort(day);

            int time = 805;

            for (ScheduleEntry e : day) {
                TextView tv = new TextView(getActivity().getApplicationContext());

                int duration = 60 * ((e.getEnd() / 100) - (e.getStart() / 100)) + (e.getEnd() % 100 - e.getStart() % 100);
                int before =  60 * ((e.getStart() / 100) - (time / 100)) + (e.getStart() % 100 - time % 100);

                duration = ((duration + 25) / 30) * 30;
                before = ((before + 25) / 30) * 30;

                final float scale = getResources().getDisplayMetrics().density;
                int blockSize = (int) (80 * scale + 0.5f);
                int height = blockSize * duration / 60;
                int margin = blockSize * before / 60;

                Log.d("tv", "blockSize = " + blockSize);
                Log.d("tv", "start = " + e.getStart());
                Log.d("tv", "end = " + e.getEnd());
                Log.d("tv", "height = " + height);
                Log.d("tv", "margin = " + margin);
                Log.d("tv", "duration = " + duration);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height);
                LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, margin);

                View v = new View(getActivity().getApplicationContext());
                v.setLayoutParams(mlp);
                dayViews[i].addView(v);

                tv.setBackgroundColor(e.getColor());
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setLayoutParams(lp);
                tv.setText(e.getName() + "\n" + e.getLocation());
                dayViews[i].addView(tv);

                time = e.getEnd();
            }
        }
    }
}