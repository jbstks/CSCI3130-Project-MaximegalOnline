package com.example.peter.a3130project.register;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

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
import java.util.HashSet;
import java.util.List;

/**
 * Deals confirming registration conflicts given courses and requested course.
 * Updates Firebase if and only if it is a valid addition.
 *
 * @author Peter Lee
 * @author Megan Gosse
 */

public class CourseRegistrationUI extends CourseRegistration{

    private DatabaseReference dbRef;
    private ArrayList<String> CRNs;
    private HashSet<String> setCRN;
    private ArrayList<CourseSection> currentCourseSections;
    private Context applicationContext;
    private CourseSection coursesection;
    private String B00;
    private FirebaseUser user;

    /**
     * Constructor
     */
    public CourseRegistrationUI(){
        super();
    }

    /**
     * Checks to see if registration is valid, and then registers course in firebase if it is
     *
     * @param fuser holds the information of the user that is logged in
     * @param cs    course section the user is trying to register for
     * @param ac    application context
     */
    public void firebaseRegister(FirebaseUser fuser, CourseSection cs, Context ac) {
        applicationContext = ac;
        coursesection = cs;
        // Fills in current_courses with the firebase instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        user = fuser;
        dbRef = db.getReference();

        // TODO setup B00 numbers for each user and query for them here

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = user.getEmail();
                B00 = null;
                int index = 0;
                for (DataSnapshot bentry : dataSnapshot.child("students").getChildren()) {
                    String Bcand = bentry.getKey();

                    if (bentry.child("email").getValue(String.class).equals(email)) {
                        B00 = Bcand;
                        break;
                    }
                }

                if (B00 == null) {
                    Toast.makeText(applicationContext, "Can't register. Not logged in.", Toast.LENGTH_SHORT).show();
                    return;
                }

                CRNs = new ArrayList<>();
                setCRN = new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.child("students").child(B00).child("courses").child("current").getChildren()) {
                    String tmp = snapshot.getValue(String.class);
                    String key = snapshot.getKey();
                    if (!setCRN.contains(tmp)) {
                        CRNs.add(tmp);
                        setCRN.add(tmp);
                        if (Integer.parseInt(key) > index ) {
                            index = Integer.parseInt(key);
                        }
                    }
                }
                // get courseSection info from CRNs
                currentCourseSections = new ArrayList<>();
                for (DataSnapshot crnSnapshot : dataSnapshot.child("crn").getChildren()) {
                    for (String CRN: CRNs) {
                        if (crnSnapshot.getKey().equals(CRN)) {
                            String sectionNum = crnSnapshot.child("section").getValue(String.class);
                            String prof = crnSnapshot.child("professor").getValue(String.class);

                            List<CourseTime> courseTimeList = new ArrayList<>();
                            //get CourseTime info
                            for (DataSnapshot timesSnapshot : crnSnapshot.child("times").getChildren()) {
                                String day = timesSnapshot.getKey();
                                String startTime = timesSnapshot.child("start").getValue(String.class);
                                String endTime = timesSnapshot.child("end").getValue(String.class);
                                String location = timesSnapshot.child("location").getValue(String.class);

                                CourseTime courseTime = new CourseTime(day, startTime, endTime, location);
                                courseTimeList.add(courseTime);
                            }

                            //get Course info
                            String code = crnSnapshot.child("code").getValue(String.class);
                            String name = crnSnapshot.child("name").getValue(String.class);
                            String semester = crnSnapshot.child("semester").getValue(String.class);
                            String year = crnSnapshot.child("year").getValue(String.class);

                            Course course = new Course(code, name, semester, year);
                            CourseSection section = new CourseSection(sectionNum, CRN, prof, course, courseTimeList);
                            currentCourseSections.add(section);
                        }
                    }
                }

                setcurrent_courses(currentCourseSections);
                if (currentCourseSections.size() == 0) {
                    Log.d("Register", "curcourse is null for some reason");
                }

                /* Try registration */
                ArrayList<CourseSection> register_result = attempt_register(coursesection);
                if (register_result == null) { //TODO: define applicationContext
                    Toast.makeText(applicationContext, "Can't register. Course is already registered for you.", Toast.LENGTH_SHORT).show();
                } else if (register_result.size() != 0) {  // There is a conflicting course. Mention the conflicting course in the output.
                    StringBuilder outputmessage = new StringBuilder("Can't register. Course conflicts with ");
                    for (int i = 0; i < register_result.size(); i++) {
                        outputmessage.append(" " + register_result.get(i).getcrn());
                    }
            
                    Toast.makeText(applicationContext, outputmessage.toString(), Toast.LENGTH_SHORT).show();
                } else { //Otherwise, register
                    try {
                        pushRegister(coursesection, index);
                    } catch (RegistrationException e) {
                        Log.d("Reigster", "Something bad happened on register");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    
        if (currentCourseSections == null) {
            Log.d("registration","currentcourse is null");
            SystemClock.sleep(100);
        }
    }

    /** pushRegister
     *-------------
     * @param course_sec:
     *    course that is desired to be registered
     **/
    public void pushRegister(CourseSection course_sec, int index) throws RegistrationException {

        /* Check to see if the course is valid */
        ArrayList<CourseSection> cs= attempt_register(course_sec);

        /* Check for bad things which shouldn't happen */
        if (cs == null) {
            throw new RegistrationException("Invalid registration state");
        }

        if (cs.size() != 0 ) {
            throw new RegistrationException("Invalid registration state");
        }

        /* Adds the course to the database*/
        dbRef.child("students").child(B00).child("courses").child("current").child((new Integer(index)).toString()).setValue(course_sec.getcrn());
    }
}
