package com.example.peter.a3130project.register;
import com.example.peter.a3130project.course.CourseSection;
import com.example.peter.a3130project.course.Course;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * @author PL,
 * @author MG
 *
 * @class CourseRegistration
 * Deals confirming registration conflicts given courses and requested course.
 * Updates firebase if and only if it is a valid addition.
 **/

public class CourseRegistrationUI extends CourseRegistration{

    public CourseRegistrationUI(FirebaseUser user) {
        super();
        //  Fills in current_courses with the firebase instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("students");

        // TODO setup B00 numbers for each user and query for them here
        DatabaseReference currentCoursesRef = ref.child("B00123456").child("courses").child("current");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get CRNs
                ArrayList<String> CRNs = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CRNs.add(snapshot.getValue(String.class));
                }

                // TODO get courseSection info and set current_courses

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
    
    /** do_register
     *-------------
     * @param course: 
     *    course that is desired to be reigstered
     **/
    public void do_register(CourseSection course_sec) {

        /* Check to see if the course is valid */
        ArrayList<CourseSection> cs= attempt_register(course_sec);

        /* Check for bad things which shouldn't happen */
        if (cs == null) {
            throw Exception;
        }

        if (cs.size() != 0 ) {
            throw Exception;
        }
        
	/* Adds the course to the database*/
	currentCoursesRef.push().setValue(course_sec.id);
    }
}
