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
    public String name;
    public int start;
    public int end;
    public String location;
    int color;

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

                        int duration = 60 * ((e.end / 100) - (e.start / 100)) + (e.end % 100 - e.start % 100);
                        int before =  60 * ((e.start / 100) - (time / 100)) + (e.start % 100 - time % 100);

                        duration = ((duration + 25) / 30) * 30;
                        before = ((before + 25) / 30) * 30;

                        final float scale = getResources().getDisplayMetrics().density;
                        int blockSize = (int) (80 * scale + 0.5f);
                        int height = blockSize * duration / 60;
                        int margin = blockSize * before / 60;

                        Log.d("tv", "blockSize = " + blockSize);
                        Log.d("tv", "start = " + e.start);
                        Log.d("tv", "end = " + e.end);
                        Log.d("tv", "height = " + height);
                        Log.d("tv", "margin = " + margin);
                        Log.d("tv", "duration = " + duration);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height);
                        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, margin);

                        View v = new View(getApplicationContext());
                        v.setLayoutParams(mlp);
                        dayViews[i].addView(v);


                        tv.setBackgroundColor(e.color);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setLayoutParams(lp);
                        tv.setText(e.name + "\n" + e.location);
                        dayViews[i].addView(tv);

                        time = e.end;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
