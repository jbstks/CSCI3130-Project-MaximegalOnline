package com.csci3130.project.maximegalonline;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.widget.TextView;

import com.csci3130.project.maximegalonline.course.CourseCrnList;
import com.csci3130.project.maximegalonline.course.Course;
import com.csci3130.project.maximegalonline.course.CourseTime;
import com.csci3130.project.maximegalonline.course.CourseSection;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();
        Log.d("COURSE", termActivityBundle.toString());

        if (termActivityBundle != null) {
            TextView codeTextView = findViewById(R.id.courseInfo_code);
            TextView nameTextView = findViewById(R.id.courseInfo_name);

            String name = (String) termActivityBundle.get("name");
            String code = (String) termActivityBundle.get("code");
            String semester = (String) termActivityBundle.get("semester");
            String year = (String) termActivityBundle.get("year");

            Log.d("COURSEINFO", "semester: " + semester + " year: " + year);

            curr_course = new Course(code, name, semester, year);
            getCourseExtra(code);

            // Database query for all sections of this course
            getSections(code, semester, year);

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
    public void getSections(String code, String inSemester, String inYear) {

        final RecyclerView section_rv = findViewById(R.id.sections_rv);
        section_rv.setHasFixedSize(true);

        Log.d("SECTIONS", "Creating RELATIVE Layout\n");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        section_rv.setLayoutManager(llm);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =
                database.getReference("available_courses2").child(inSemester + " " + inYear).child(code);

        Query query = myRef.child("sections");

        final List<CourseCrnList> sectionCrns = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // List<CourseSection> sections = new ArrayList<>();
                    for (DataSnapshot section : dataSnapshot.getChildren()) {
                        String sectionNum = section.getKey();
                        String crn = section.getValue(String.class);

                        CourseCrnList sectionCrn = new CourseCrnList(sectionNum, crn);
                        sectionCrns.add(sectionCrn);
                    }

                    Log.d("SECTION", "getting crns finished");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference myRef2 = database.getReference("crn");
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    List<CourseSection> sections = new ArrayList<>();
                    for (int i=0; i<sectionCrns.size(); i++) {

                        String sectionNum = sectionCrns.get(i).getSection();
                        String crn = sectionCrns.get(i).getcrn();
                        Log.d("SECTIONS", "Looking up the crn" + " " + crn);
                        String professor = dataSnapshot.child(crn).child("professor").getValue(String.class);
                        int capacity = dataSnapshot.child(crn).child("capacity").getValue(Integer.class);
                        int enrolled = dataSnapshot.child(crn).child("enrolled").getValue(Integer.class);
                        List<CourseTime> courseTimes = new ArrayList<>();

                        for(DataSnapshot time : dataSnapshot.child(crn).child("times").getChildren()) {
                            String day = time.getKey();
                            String start = time.child("start").getValue(String.class);
                            String end = time.child("end").getValue(String.class);
                            String location = time.child("location").getValue(String.class);
                            Log.d("SECTIONS", "Found values" + day + " " + start + " " + end + " " + location);
                            CourseTime courseTime = new CourseTime(day, start, end, location);
                            courseTimes.add(courseTime);
                        }

                        CourseSection courseSection= new CourseSection(enrolled, capacity, sectionNum, crn, professor, curr_course, courseTimes);
                        Log.d("COURSEINFO","values of sometimes broken if " + courseSection.getcourse().getsemester()
                                + " " + courseSection.getcourse().getyear());
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
