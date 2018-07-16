package com.example.peter.a3130project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.util.Log;

import android.widget.TextView;
import com.example.peter.a3130project.register.CourseRegistrationUI;
import com.example.peter.a3130project.course.Course;
import com.example.peter.a3130project.course.CourseTime;
import com.example.peter.a3130project.course.CourseSection;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** CourseInfo
 *
 * Class for showing the course info information
 *
 * @author Peter Lee
 * @author Aecio Cavalcanti
 * @author Joanna Bistekos
 * @author Dawson Wilson
 **/
public class CourseInfo extends AppCompatActivity {

    /* global variables for this activity */
    private Course curr_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();
        Log.d("COURSE", termActivityBundle.toString());


        if (termActivityBundle != null) {
            TextView codeTextView = (TextView) findViewById(R.id.courseInfo_code);
            TextView nameTextView = (TextView) findViewById(R.id.courseInfo_name);

            String name = (String) termActivityBundle.get("name");
            String code = (String) termActivityBundle.get("code");
            String semester = (String) termActivityBundle.get("semester");
            String year = (String) termActivityBundle.get("year");

            curr_course = new Course(code, name, semester, year);

            // TODO: query for the extra course information under the course_listings database
            getCourseExtra(code);

            // Database query for all sections of this course
            getSections(code, semester, year);
            // TODO: build a dynamic place to show all the sections and a button to register for a specific section

            codeTextView.setText(code);
            nameTextView.setText(name);
        }
    }

    public void getCourseExtra(String code) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("course_listings").child(code);

        Log.d("COURSEEXTRA", "getting the course extras of code: " + code);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String description = dataSnapshot.child("description").getValue(String.class);
                Log.d("COURSEEXTRA", "Found description: \n" + description);
                String prerequisites = "";

                for (DataSnapshot course : dataSnapshot.child("prerequisites").getChildren()) {
                    String courseCode = course.getValue(String.class);
                    prerequisites = prerequisites + courseCode + "\n";
                    Log.d("COURSEEXTRA", "Found prerequisite: " + courseCode);
                }

                if ( description != null) {
                    TextView descriptionTextView = findViewById(R.id.courseInfo_description);
                    descriptionTextView.setText(description);
                }
                if ( ! prerequisites.equals("") ) {
                    TextView prerequisitesTextView = findViewById(R.id.courseInfo_prerequisites);
                    prerequisitesTextView.setText(prerequisites);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("COURSEEXTRA", "Done getting extras for: " + code);
    }


    /**
     * Queries data from the database based upon supplied information name, semester, year
     */
    public void getSections(String code, String semester, String year) {

        final RecyclerView section_rv = findViewById(R.id.sections_rv);
        section_rv.setHasFixedSize(true);

        Log.d("SECTIONS", "Creating RELATIVE Layout\n");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        section_rv.setLayoutManager(llm);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =
                database.getReference("available_courses1").child(semester + " " + year).child(code);

        Query query = myRef.child("sections");

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    List<CourseSection> sections = new ArrayList<>();
                    for (DataSnapshot section : dataSnapshot.getChildren()) {

                        int capacity = section.child("capacity").getValue(Integer.class);
                        String sectionNum = section.getKey();
                        String crn = section.child("crn").getValue(String.class);
                        String professor = section.child("professor").getValue(String.class);
                        List<CourseTime> courseTimes = new ArrayList<>();

                        for(DataSnapshot time : section.child("times").getChildren()) {
                            String day = time.getKey();
                            String start = time.child("start").getValue(String.class);
                            String end = time.child("end").getValue(String.class);
                            String location = time.child("location").getValue(String.class);
                            Log.d("SECTIONS", "Found values" + day + " " + start + " " + end + " " + location);
                            CourseTime courseTime = new CourseTime(day, start, end, location);
                            courseTimes.add(courseTime);
                        }

                        CourseSection courseSection= new CourseSection(capacity, sectionNum, crn, professor, curr_course, courseTimes);
                        sections.add(courseSection);
                    }

                    SectionRVAdapter sectionRVAdapter = new SectionRVAdapter(sections);
                    section_rv.setAdapter(sectionRVAdapter);

                    Log.d("SECTION", "finished");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
