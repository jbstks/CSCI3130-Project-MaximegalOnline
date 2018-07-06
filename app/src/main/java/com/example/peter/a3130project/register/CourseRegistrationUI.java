package com.example.peter.a3130project.register;
import com.example.peter.a3130project.SectionRVAdapter;
import com.example.peter.a3130project.course.CourseSection;
import com.example.peter.a3130project.course.Course;

import com.example.peter.a3130project.course.CourseTime;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PL,
 * @author MG
 *
 * @class CourseRegistration
 * Deals confirming registration conflicts given courses and requested course.
 * Updates firebase if and only if it is a valid addition.
 **/

public class CourseRegistrationUI extends CourseRegistration{

    private DatabaseReference currentCoursesRef;
    private DatabaseReference availableCoursesRef;
    private ArrayList<String> CRNs;
    private ArrayList<CourseSection> currentCourseSections;
    private int i;

    public CourseRegistrationUI(FirebaseUser user) {
        super();
        //  Fills in current_courses with the firebase instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference studentsRef = db.getReference("students");
        availableCoursesRef = db.getReference("available_courses1");

        // TODO setup B00 numbers for each user and query for them here
        currentCoursesRef = studentsRef.child("B00123456").child("courses").child("current");

        currentCoursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get CRNs
                CRNs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CRNs.add(snapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        availableCoursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                i = 0;
                //get courseSection info from CRNs
                currentCourseSections = new ArrayList<>();
                //TODO refactor database structure to avoid nested loops
                //loop through semesters
                for (DataSnapshot semesterSnapshot : dataSnapshot.getChildren()) {
                    //loop through courses
                    for (DataSnapshot courseSnapshot : semesterSnapshot.getChildren()) {
                        //loop through sections
                        for (DataSnapshot sectionSnapshot : courseSnapshot.child("sections").getChildren()) {
                            if (sectionSnapshot.child("crn").getValue(String.class).equals(CRNs.get(i))) {
                                String sectionNum = sectionSnapshot.getKey();
                                String CRN = CRNs.get(i);
                                String prof = sectionSnapshot.child("professor").getValue(String.class);

                                List<CourseTime> courseTimeList = new ArrayList<>();
                                //get CourseTime info
                                for (DataSnapshot timesSnapshot : sectionSnapshot.child("times").getChildren()) {
                                    String day = timesSnapshot.getKey();
                                    String startTime = timesSnapshot.child("start").getValue(String.class);
                                    String endTime = timesSnapshot.child("end").getValue(String.class);
                                    String location = timesSnapshot.child("location").getValue(String.class);

                                    CourseTime courseTime = new CourseTime(day, startTime, endTime, location);
                                    courseTimeList.add(courseTime);
                                }

                                //get Course info
                                String code = courseSnapshot.getKey();
                                String name = courseSnapshot.child("name").getValue(String.class);
                                String semester = courseSnapshot.child("semester").getValue(String.class);
                                String year = courseSnapshot.child("year").getValue(String.class);
                                Course course = new Course(code, name, semester, year);

                                CourseSection section = new CourseSection(sectionNum, CRN, prof, course, courseTimeList);
                                currentCourseSections.add(section);
                            }
                            else {
                                i++;
                            }
                        }
                    }
                }
                setcurrent_courses(currentCourseSections);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    /** do_register
     *-------------
     * @param course_sec:
     *    course that is desired to be reigstered
     **/
    public void do_register(CourseSection course_sec) throws Exception {

        /* Check to see if the course is valid */
        ArrayList<CourseSection> cs= attempt_register(course_sec);

        /* Check for bad things which shouldn't happen */
        if (cs == null) {
            throw new Exception("Invalid registration state");
        }

        if (cs.size() != 0 ) {
            throw new Exception("Invalid registration state");
        }

	/* Adds the course to the database*/
	currentCoursesRef.push().setValue(course_sec.getcrn());
    }
}
