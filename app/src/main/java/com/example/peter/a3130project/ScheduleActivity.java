package com.example.peter.a3130project;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

class ScheduleEntry implements Comparable<ScheduleEntry> {
    private String name;
    private int start;
    private int end;
    private String location;
    private int color;

    /**
     * Constructor to create a schedule entry
     *
     * @param name      Course name
     * @param start     Start time of course
     * @param end       End time of course
     * @param location  Lecture location
     * @param color     Color to represent schedule entry
     */
    public ScheduleEntry(String name, int start, int end, String location, int color) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.location = location;
        this.color = color;
    }

    @Override
    public int compareTo(ScheduleEntry e) {
        if (start < e.start) return -1;
        if (start > e.start) return  1;
        return 0;
    }

    /* getters and setters */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStart() { return start; }
    public void setStart(int start) { this.start = start; }

    public int getEnd() { return end; }
    public void setEnd(int end) { this.end = end; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }
}

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        final LinearLayout l = findViewById(R.id.monday);

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        db.getReference().child("available_courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> courses = (Map<String, Object>) dataSnapshot.getValue();

                final LinearLayout[] dayViews = {
                    findViewById(R.id.monday),
                    findViewById(R.id.tuesday),
                    findViewById(R.id.wednesday),
                    findViewById(R.id.thursday),
                    findViewById(R.id.friday)
                };

                final ArrayList<String> days = new ArrayList<String>(Arrays.asList("mon", "tue", "wed", "thu", "fri"));

                final String[] colors = { "#ffe8e8", "#f0f0da", "#d3eabb", "#a9c4a0" };
                int colorIndex = 0;

                final ArrayList<ArrayList<ScheduleEntry>> schedule = new ArrayList<>();
                for (int i = 0; i < 5; ++i)
                    schedule.add(new ArrayList<ScheduleEntry>());

                for (Map.Entry<String, Object> e : courses.entrySet()) {
                    Map<String, Object> c = (Map<String, Object>) e.getValue();
                    String id = (String) c.get("course");
                    Map<String, Object> times = (Map<String, Object>) c.get("times");

                    int color = Color.parseColor(colors[colorIndex++ % colors.length]);

                    for (Map.Entry<String, Object> t : times.entrySet()) {
                        int i = days.indexOf(t.getKey());
                        Map<String, Object> v = (Map<String, Object>) t.getValue();
                        int start = Integer.parseInt((String)v.get("start"));
                        int end = Integer.parseInt((String)v.get("end"));
                        String location = (String)v.get("loc");
                        schedule.get(i).add(new ScheduleEntry(id, start, end, location, color));
                    }
                }

                for (int i = 0; i < 5; ++i) {
                    ArrayList<ScheduleEntry> day = schedule.get(i);
                    Collections.sort(day);

                    int time = 805;

                    for (ScheduleEntry e : day) {
                        TextView tv = new TextView(getApplicationContext());

                        int duration = 60 * ((e.getEnd() / 100) - (e.getStart() / 100)) + (e.getEnd() % 100 - e.getStart() % 100);
                        int before =  60 * ((e.getStart() / 100) - (time / 100)) + (e.getStart() % 100 - time % 100);

                        duration = ((duration + 25) / 30) * 30;
                        before = ((before + 25) / 30) * 30;

                        final float scale = getResources().getDisplayMetrics().density;
                        int blockSize = (int) (80 * scale + 0.5f);
                        int height = blockSize * duration / 60;
                        int margin = blockSize * before / 60;

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height);
                        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, margin);

                        View v = new View(getApplicationContext());
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
